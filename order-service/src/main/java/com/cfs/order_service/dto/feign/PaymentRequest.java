package com.cfs.order_service.dto.feign;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private String orderNumber;

    private Long customerId;

    private BigDecimal amount;

}