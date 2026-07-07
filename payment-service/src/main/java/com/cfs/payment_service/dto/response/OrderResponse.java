package com.cfs.payment_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private Long customerId;

    private BigDecimal totalAmount;

    private String orderStatus;

    private LocalDateTime createdAt;
}