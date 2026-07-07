package com.cfs.order_service.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUser {

    private Long id;

    private String name;

    private String email;

    private String role;

}