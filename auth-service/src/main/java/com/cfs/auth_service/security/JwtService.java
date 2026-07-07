package com.cfs.auth_service.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwt;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwt);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .signWith(getKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token)
                .getSubject();

    }
    public String extractUserId( String token) {
        return extractAllClaims(token)
                .getId();
    }
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    public boolean isTokenValid(String token, String email) {

        return email.equals(extractUsername(token))
                && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());

    }
}
