package com.gardenmanager.database_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

public class JwtUtil {
        
    // private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); // Generates a secure key
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("YourSuperSecretKeyMustBeAtLeast32CharactersLong".getBytes());

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
