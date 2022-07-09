package com.exam.common.utils;

import com.exam.security.configuration.properties.CookieProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class HttpHelper {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final CookieProperties cookieProperties;

    public Optional<String> extractTokenCookie() {
        return getCookie(cookieProperties.getJwtCookieName())
                .map(Cookie::getValue)
                .filter(StringUtils::hasText);
    }

    public Optional<Cookie> getCookie(String name) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst();
    }

    public void addCookie(String name, String value, int maxAge) {
        var cookie = createCookie(name, value, maxAge);
        response.addCookie(cookie);
    }

    private Cookie createCookie(String name, String value, int maxAge) {
        var cookie = new Cookie(name, value);
        var cookiePath = request.getContextPath() + "/";
        cookie.setPath(cookiePath);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public void deleteCookies(String... names) {
        for (String name : names) {
            getCookie(name).ifPresent(cookie -> {
                cookie.setValue("");
                var cookiePath = request.getContextPath() + "/";
                cookie.setPath(cookiePath);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            });
        }
    }

    public String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    public <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
