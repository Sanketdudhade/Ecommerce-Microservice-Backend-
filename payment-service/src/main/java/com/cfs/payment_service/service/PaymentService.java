package com.cfs.payment_service.service;

import com.cfs.payment_service.dto.request.CreatePaymentRequest;
import com.cfs.payment_service.dto.request.RefundPaymentRequest;
import com.cfs.payment_service.dto.request.VerifyPaymentRequest;
import com.cfs.payment_service.dto.response.PaymentHistoryResponse;
import com.cfs.payment_service.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse verifyPayment(VerifyPaymentRequest request);

    PaymentResponse refundPayment(RefundPaymentRequest request);

    PaymentResponse cancelPayment(Long paymentId);

    PaymentResponse getPaymentById(Long paymentId);

    PaymentResponse getPaymentByOrderId(Long orderId);

    List<PaymentHistoryResponse> getCurrentUserPayments();

}