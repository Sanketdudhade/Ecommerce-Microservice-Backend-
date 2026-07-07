package com.cfs.payment_service.security;

public interface JwtService {

    Long extractUserId(String token);

    String extractUsername(String token);
}