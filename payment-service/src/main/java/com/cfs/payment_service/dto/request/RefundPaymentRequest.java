package com.cfs.payment_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundPaymentRequest {

    @NotNull(message = "Payment Id is required")
    private Long paymentId;

    @NotNull(message = "Refund Amount is required")
    @DecimalMin(value = "1.00", message = "Refund amount must be greater than zero")
    private BigDecimal refundAmount;

    private String remarks;

}