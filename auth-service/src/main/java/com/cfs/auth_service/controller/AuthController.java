package com.cfs.auth_service.controller;

import com.cfs.auth_service.common.ApiResponse;
import com.cfs.auth_service.dto.request.LoginRequest;
import com.cfs.auth_service.dto.request.RegisterRequest;
import com.cfs.auth_service.dto.response.LoginResponse;
import com.cfs.auth_service.dto.response.UserResponse;
import com.cfs.auth_service.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse response= authService.register(registerRequest);
        ApiResponse<UserResponse> apiResponse=ApiResponse.<UserResponse>builder().success(true)
                .message("user registered successfully")
                .data(response).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response= authService.login(loginRequest);
        ApiResponse<LoginResponse> apiResponse=ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login Successful")
                .data(response).build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile accessed successfully";
    }
}
