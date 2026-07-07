package com.cfs.auth_service.service.impl;


import com.cfs.auth_service.dto.request.LoginRequest;
import com.cfs.auth_service.dto.request.RegisterRequest;
import com.cfs.auth_service.dto.response.LoginResponse;
import com.cfs.auth_service.dto.response.UserResponse;
import com.cfs.auth_service.entity.User;
import com.cfs.auth_service.enums.Role;
import com.cfs.auth_service.exception.InvalidCredentialsException;
import com.cfs.auth_service.exception.UserAlreadyExistsException;
import com.cfs.auth_service.repository.UserRepository;
import com.cfs.auth_service.security.JwtService;
import com.cfs.auth_service.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if (userRepository.existsByPhone(registerRequest.getPhone())) {
            throw new UserAlreadyExistsException("Phone already exists");
        }
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .role(Role.CUSTOMER)
                .build();
        User userCreated = userRepository.save(user);

        return UserResponse.builder()
                .id(userCreated.getId())
                .firstName(userCreated.getFirstName())
                .lastName(userCreated.getLastName())
                .email(userCreated.getEmail())
                .phone(userCreated.getPhone())
                .role(Role.CUSTOMER)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new InvalidCredentialsException(""));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            throw new InvalidCredentialsException("Wrong password");
        }
        String token=jwtService.generateToken(user.getEmail());

        UserResponse response=UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getFirstName())
                .email(user.getEmail())
                .id(user.getId())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(response)
                .build();

    }
}
