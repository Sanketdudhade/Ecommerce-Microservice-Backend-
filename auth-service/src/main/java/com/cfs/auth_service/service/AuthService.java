package com.cfs.auth_service.service;

import com.cfs.auth_service.dto.request.LoginRequest;
import com.cfs.auth_service.dto.request.RegisterRequest;
import com.cfs.auth_service.dto.response.LoginResponse;
import com.cfs.auth_service.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
