package com.cfs.payment_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPaymentRequest {

    @NotBlank(message = "Payment Reference is required")
    private String paymentReference;

    @NotBlank(message = "Razorpay Order Id is required")
    private String razorpayOrderId;

    @NotBlank(message = "Razorpay Payment Id is required")
    private String razorpayPaymentId;

    @NotBlank(message = "Razorpay Signature is required")
    private String razorpaySignature;

}