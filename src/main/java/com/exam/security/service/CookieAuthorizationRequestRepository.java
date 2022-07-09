package com.exam.security.service;

import com.exam.common.utils.HttpHelper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int COOKIE_EXPIRATION_IN_SECONDS = 180;

    private final HttpHelper httpHelper;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest httpServletRequest) {
        return httpHelper.getCookie(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> httpHelper.deserialize(cookie, OAuth2AuthorizationRequest.class)).orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            httpHelper.deleteCookies(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, REDIRECT_URI_PARAM_COOKIE_NAME);
        } else {
            var authRequestCookie = httpHelper.serialize(authorizationRequest);
            httpHelper.addCookie(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, authRequestCookie, COOKIE_EXPIRATION_IN_SECONDS);
            String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
            if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
                httpHelper.addCookie(REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRATION_IN_SECONDS);
            }
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies() {
        httpHelper.deleteCookies(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, REDIRECT_URI_PARAM_COOKIE_NAME);
    }

    public Optional<String> getRedirectUriCookieValue() {
        return httpHelper.getCookie(REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
    }
}
