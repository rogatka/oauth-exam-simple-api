package com.exam.security.configuration.filter;

import com.exam.common.utils.HttpHelper;
import com.exam.exams.repository.UserRepository;
import com.exam.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String REGISTRATION_ID = "google";

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final JwtDecoderFactory<ClientRegistration> jwtDecoderFactory;

    private final UserRepository userRepository;

    private final HttpHelper httpHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        httpHelper.extractTokenCookie()
                .flatMap(this::validate)
                .ifPresent(this::authenticate);
        filterChain.doFilter(request, response);
    }

    private Optional<Jwt> validate(String token) {
        try {
            var clientRegistration = clientRegistrationRepository.findByRegistrationId(REGISTRATION_ID);
            var tokenDecoder = jwtDecoderFactory.createDecoder(clientRegistration);
            return Optional.of(tokenDecoder.decode(token));
        } catch (JwtValidationException e) {
            log.debug("Token validation error. (Reason: {}) (Token: {})", e.getMessage(), token);
            log.debug(e.getMessage(), e);
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Authentication failed. (Reason: {}) (Token: {})", e.getMessage(), token);
            log.debug(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private void authenticate(Jwt jwt) {
        userRepository.findByEmail(jwt.getClaimAsString("email"))
                .map(user -> UserPrincipal.from(user, jwt))
                .ifPresent(principal -> {
                    var auth = new OAuth2AuthenticationToken(principal, principal.getAuthorities(), REGISTRATION_ID);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
    }
}
