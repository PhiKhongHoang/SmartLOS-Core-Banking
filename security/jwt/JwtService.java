package com.ktn3.core_banking.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ktn3.core_banking.security.model.CustomUserPrincipal;
import com.ktn3.core_banking.security.service.CustomUserDetailsService;
import com.ktn3.core_banking.user.entity.User;

import javax.crypto.SecretKey;

import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret-key}")
    private String secretKey;

    @Value("${app.jwt.access-token-expiration}")
    private Long accessExpiration;

    @Value("${app.jwt.refresh-token-expiration}")
    private Long refreshExpiration;

    private SecretKey getKey() {

        return Keys.hmacShaKeyFor(
                secretKey.getBytes(
                        StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(r -> r.getCode())
                .toList();

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", String.valueOf(user.getId()))
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }
    
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(
            String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    public Claims parseClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(
            String token,
            UserDetails userDetails) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername());
    }
}
