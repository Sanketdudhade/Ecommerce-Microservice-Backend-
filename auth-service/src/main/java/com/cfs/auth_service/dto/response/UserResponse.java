package com.cfs.auth_service.dto.response;

import com.cfs.auth_service.enums.Role;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;

}
