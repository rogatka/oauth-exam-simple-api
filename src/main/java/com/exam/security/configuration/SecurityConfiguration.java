package com.exam.security.configuration;


import com.exam.common.utils.HttpHelper;
import com.exam.exams.repository.UserRepository;
import com.exam.security.configuration.filter.TokenAuthenticationFilter;
import com.exam.security.configuration.properties.CookieProperties;
import com.exam.security.service.CookieAuthorizationRequestRepository;
import com.exam.security.service.CustomOidcUserService;
import com.exam.security.service.OidcAuthenticationFailureHandler;
import com.exam.security.service.OidcAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CookieAuthorizationRequestRepository authorizationRequestRepository;

    private final CookieProperties cookieProperties;

    private final OidcAuthenticationSuccessHandler authenticationSuccessHandler;

    private final OidcAuthenticationFailureHandler authenticationFailureHandler;

    private final CustomOidcUserService oidcUserService;

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final JwtDecoderFactory<ClientRegistration> jwtDecoderFactory;

    private final HttpHelper httpHelper;

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .authorizeRequests(a -> a
                        .antMatchers("/", "/actuator/**", "/swagger-ui/**", "error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(authorizationRequestRepository)
                .and()
                .userInfoEndpoint()
                .oidcUserService(oidcUserService)
                .and()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .deleteCookies(cookieProperties.getJwtCookieName())
                .logoutSuccessHandler(logoutSuccessHandler());

        httpSecurity.addFilterBefore(new TokenAuthenticationFilter(clientRegistrationRepository, jwtDecoderFactory, userRepository, httpHelper),
                UsernamePasswordAuthenticationFilter.class);
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        var logoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        logoutSuccessHandler.setPostLogoutRedirectUri("/");
        return logoutSuccessHandler;
    }
}
