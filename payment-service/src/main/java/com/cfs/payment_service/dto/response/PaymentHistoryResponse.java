package com.cfs.payment_service.dto.response;

import com.cfs.payment_service.enums.PaymentMethod;
import com.cfs.payment_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistoryResponse {

    private Long paymentId;

    private Long orderId;

    private String paymentReference;

    private BigDecimal amount;

    private String currency;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

}