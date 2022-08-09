package com.exam.security.configuration.oidc;

import com.exam.security.model.UserPrincipal;
import com.exam.security.configuration.properties.CookieProperties;
import com.exam.security.configuration.properties.TokenProperties;
import com.exam.common.utils.HttpHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OidcAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    private final HttpHelper httpHelper;

    private final CookieProperties cookieProperties;

    private final TokenProperties tokenProperties;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        addTokenCookie(authentication);
        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return cookieAuthorizationRequestRepository.getRedirectUriCookieValue()
                .orElse(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
    }

    private void addTokenCookie(Authentication authentication) {
        OidcIdToken jwt = ((UserPrincipal) authentication.getPrincipal()).getIdToken();
        httpHelper.addCookie(cookieProperties.getJwtCookieName(), jwt.getTokenValue(), tokenProperties.getLifetimeMillis());
    }
}
