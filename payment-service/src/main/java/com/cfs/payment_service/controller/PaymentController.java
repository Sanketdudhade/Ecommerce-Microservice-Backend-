package com.cfs.payment_service.controller;

import com.cfs.payment_service.constant.ApiConstants;
import com.cfs.payment_service.dto.request.CreatePaymentRequest;
import com.cfs.payment_service.dto.request.RefundPaymentRequest;
import com.cfs.payment_service.dto.request.VerifyPaymentRequest;
import com.cfs.payment_service.dto.response.ApiResponse;
import com.cfs.payment_service.dto.response.PaymentHistoryResponse;
import com.cfs.payment_service.dto.response.PaymentResponse;
import com.cfs.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.BASE_URL)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create Razorpay Order
     */
    @PostMapping(ApiConstants.CREATE_PAYMENT)
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        PaymentResponse response = paymentService.createPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment order created successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Verify Razorpay Payment
     */
    @PostMapping(ApiConstants.VERIFY_PAYMENT)
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequest request) {

        PaymentResponse response = paymentService.verifyPayment(request);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment verified successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Refund Payment
     */
    @PostMapping(ApiConstants.REFUND_PAYMENT)
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(
            @Valid @RequestBody RefundPaymentRequest request) {

        PaymentResponse response = paymentService.refundPayment(request);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Refund initiated successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Cancel Payment
     */
    @PatchMapping(ApiConstants.CANCEL_PAYMENT)
    public ResponseEntity<ApiResponse<PaymentResponse>> cancelPayment(
            @PathVariable Long paymentId) {

        PaymentResponse response = paymentService.cancelPayment(paymentId);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment cancelled successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Get Payment By Id
     */
    @GetMapping(ApiConstants.GET_PAYMENT)
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Long paymentId) {

        PaymentResponse response = paymentService.getPaymentById(paymentId);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment fetched successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Get Payment By Order Id
     */
    @GetMapping(ApiConstants.GET_PAYMENT_BY_ORDER)
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByOrderId(
            @PathVariable Long orderId) {

        PaymentResponse response = paymentService.getPaymentByOrderId(orderId);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment fetched successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Logged-in User Payment History
     */
    @GetMapping(ApiConstants.PAYMENT_HISTORY)
    public ResponseEntity<ApiResponse<List<PaymentHistoryResponse>>> getCurrentUserPayments() {

        List<PaymentHistoryResponse> history =
                paymentService.getCurrentUserPayments();

        return ResponseEntity.ok(
                ApiResponse.<List<PaymentHistoryResponse>>builder()
                        .success(true)
                        .message("Payment history fetched successfully.")
                        .data(history)
                        .build());
    }

}