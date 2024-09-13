package com.FrancisJones.NextWorkout.config;

import com.FrancisJones.NextWorkout.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    public JwtSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> {
                    if (!(auth.getPrincipal() instanceof OAuth2User)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid authentication type");
                    }
                    return (OAuth2User) auth.getPrincipal();
                })
                .flatMap(oAuth2User -> {
                    String email = oAuth2User.getAttribute("email");
                    String name = oAuth2User.getAttribute("name");

                    if (email == null || name == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required attributes"));
                    }

                    Map<String, Object> claims = new HashMap<>();
                    claims.put("email", email);
                    claims.put("name", name);

                    String jwtToken = jwtUtil.generateToken(claims, email);
                    exchange.getExchange().getResponse().getHeaders().set("Authorization", "Bearer" + jwtToken);
                    exchange.getExchange().getResponse().getHeaders().setLocation(URI.create("http://localhost:5173/"));
                    return exchange.getExchange().getResponse().setComplete();
                });
    }
}
