package com.FrancisJones.NextWorkout.utils;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtil {

    private SecretKey key;
    @Value("${JWT_KEY}")
    private String jwtKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtKey.getBytes();
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .and()
                .signWith(key)
                .compact();
    }

}
