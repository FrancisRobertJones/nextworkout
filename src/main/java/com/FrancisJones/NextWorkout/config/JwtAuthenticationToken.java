package com.FrancisJones.NextWorkout.config;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String principal;
    private final Map<String, Object> claims;

    public JwtAuthenticationToken(String principal, Map<String, Object> claims) {
        super(null);
        this.principal = principal;
        this.claims = claims;
    }

    public JwtAuthenticationToken(String principal, Map<String, Object> claims, Collection<? extends GrantedAuthority> authorities, String pincipal) {
        super(authorities);
        this.principal = principal;
        this.claims = claims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }

}
