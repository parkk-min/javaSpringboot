package com.example.authen_session.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey secretKey;
    public JwtUtil(@Value("${jwt.secret.key}") String scretKey) {
        this.secretKey = new SecretKeySpec(scretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    public String createToken(String username, String role, Long expiration) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build().
                parseSignedClaims(token).getPayload().get("username").toString();
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build().
                parseSignedClaims(token).getPayload().get("role").toString();
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build()
                .parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }



}
