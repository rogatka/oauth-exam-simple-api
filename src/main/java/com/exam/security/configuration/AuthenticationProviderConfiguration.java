package com.exam.security.configuration;

import com.exam.security.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;

@Configuration
public class AuthenticationProviderConfiguration {

    @Bean
    public AuthenticationProvider authenticationProvider(CustomOidcUserService oidcUserService) {
        var accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        var provider = new OidcAuthorizationCodeAuthenticationProvider(accessTokenResponseClient, oidcUserService);
        provider.setJwtDecoderFactory(jwtDecoderFactory());
        return provider;
    }

    @Bean
    public JwtDecoderFactory<ClientRegistration> jwtDecoderFactory() {
        return new OidcIdTokenDecoderFactory();
    }
}
