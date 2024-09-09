package com.FrancisJones.NextWorkout.config;

import com.FrancisJones.NextWorkout.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class JwtSuccessHandler implements ServerAuthenticationSuccessHandler {
    final private JwtUtil jwtUtil;

    public JwtSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("name", name);

        String jwtToken = jwtUtil.generateToken(claims, email);
        exchange.getExchange().getResponse().getHeaders().set("Authorization", "Bearer" + jwtToken);
        exchange.getExchange().getResponse().getHeaders().setLocation(URI.create("http://localhost:5173/"));
        return Mono.empty();
    }
}
