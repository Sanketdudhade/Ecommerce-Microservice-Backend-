package com.cfs.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "First name is not empty")
    private String firstName;

    @NotBlank(message = "Last Name is not empty")
    private String lastName;

    @Email(message = "Invalid Email")
    @NotBlank(message = " Email is not blank")
    private String email;

    @NotBlank(message = " Password cannot blank")
    private String password;

    @NotBlank(message = "phone number cannot blank")
    private String phone;
}
