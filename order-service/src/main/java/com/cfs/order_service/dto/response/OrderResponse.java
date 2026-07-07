package com.cfs.order_service.dto.response;

import com.cfs.order_service.enums.OrderStatus;
import com.cfs.order_service.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private Long customerId;

    private String customerName;

    private String customerEmail;

    private String shippingAddress;

    private String phoneNumber;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private List<OrderItemResponse> items;

    private LocalDateTime createdAt;

}