package com.cfs.payment_service.dto.response;

import com.cfs.payment_service.enums.PaymentGateway;
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
public class PaymentResponse {

    private Long id;

    private Long orderId;

    private Long customerId;

    private String paymentReference;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    /**
     * Razorpay Key Id required by frontend
     * Example: rzp_test_xxxxxxxxx
     */
    private String razorpayKey;

    private BigDecimal amount;

    private String currency;

    private PaymentMethod paymentMethod;

    private PaymentGateway paymentGateway;

    private PaymentStatus paymentStatus;

    private String failureReason;

    private String remarks;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}