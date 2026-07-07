package com.cfs.order_service.dto.response;

import com.cfs.order_service.enums.OrderStatus;
import com.cfs.order_service.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSummaryResponse {

    private Long id;

    private String orderNumber;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

}