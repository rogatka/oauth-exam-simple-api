package com.exam.security.model;

import com.exam.exams.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class UserPrincipal implements OidcUser {

    @ToString.Include
    @EqualsAndHashCode.Include
    private final User user;

    private OidcUser oidcUser;

    @Override
    public Map<String, Object> getClaims() {
        return Optional.ofNullable(oidcUser).map(OidcUser::getClaims).orElse(null);
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return Optional.ofNullable(oidcUser).map(OidcUser::getUserInfo).orElse(null);
    }

    @Override
    public OidcIdToken getIdToken() {
        return Optional.ofNullable(oidcUser).map(OidcUser::getIdToken).orElse(null);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Optional.ofNullable(oidcUser).map(OidcUser::getAttributes).orElse(null);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user);
    }

    public static UserPrincipal from(User user, Jwt jwt) {
        var userPrincipal = new UserPrincipal(user);
        var token = OidcIdToken
                .withTokenValue(jwt.getTokenValue())
                .claims(map -> map.putAll(jwt.getClaims()))
                .build();
        userPrincipal.setOidcUser(new DefaultOidcUser(user.getAuthorities(), token));
        return userPrincipal;
    }
}
