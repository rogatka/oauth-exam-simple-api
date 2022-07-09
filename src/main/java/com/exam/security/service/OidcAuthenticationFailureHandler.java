package com.exam.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OidcAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        String redirectUri = cookieAuthorizationRequestRepository.getRedirectUriCookieValue().orElse("/");
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("authenticationError", exception.getMessage())
                .build()
                .toUriString();
        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
