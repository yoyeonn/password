package com.travel.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Must be at least 256 bits for HS256
    private static final String SECRET = "mySuperSecretKey1234567890123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long jwtExpirationMs = 86400000; // 1 day

    // Generate JWT token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256) // ✅ new API
                .compact();
    }

    // Validate token and return claims
    public Claims validateToken(String token) {
        return Jwts.parserBuilder() // ✅ new parserBuilder
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get email (subject) from token
    public String getEmailFromToken(String token) {
        return validateToken(token).getSubject();
    }

    // Get role from token
    public String getRoleFromToken(String token) {
        return (String) validateToken(token).get("role");
    }
}
