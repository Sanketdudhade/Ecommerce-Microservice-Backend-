package com.cfs.order_service.dto.feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    private String email;

    private String customerName;

    private String orderNumber;

    private String message;

}