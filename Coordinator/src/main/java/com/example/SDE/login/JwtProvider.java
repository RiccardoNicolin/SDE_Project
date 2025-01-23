package com.example.SDE.login;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.SDE.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

    static SecretKey key = Keys.hmacShaKeyFor(config.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
        String jwt = Jwts.builder()
                .claim("username", auth.getName())
                .claim("id", auth.getCredentials())
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();
        return jwt;

    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

    @SuppressWarnings("deprecation")
    public static String getUsernameFromJwtToken(String jwt) {
        jwt = jwt.substring(7); // Assuming "Bearer " is removed from the token
        try {
            Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String user = String.valueOf(claims.get("username"));
            System.out.println("Username extracted from JWT: " + claims);
            return user;
        } catch (Exception e) {
            System.err.println("Error extracting email from JWT: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
