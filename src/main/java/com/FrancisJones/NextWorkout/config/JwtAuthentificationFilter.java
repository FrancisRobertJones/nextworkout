package com.FrancisJones.NextWorkout.config;

import com.FrancisJones.NextWorkout.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthentificationFilter extends AuthenticationWebFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthentificationFilter(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain chain) {
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.extractAllClaims(jwt);
                String username = jwtUtil.getSubject(jwt);
                if (username != null) {
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(username, claims);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                return Mono.error(e);
            }
        }
        return chain.filter(serverWebExchange);
    }
}
