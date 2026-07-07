package com.cfs.payment_service.service.impl;

import com.cfs.payment_service.dto.request.CreatePaymentRequest;
import com.cfs.payment_service.dto.request.RefundPaymentRequest;
import com.cfs.payment_service.dto.request.VerifyPaymentRequest;
import com.cfs.payment_service.dto.response.PaymentHistoryResponse;
import com.cfs.payment_service.dto.response.PaymentResponse;
import com.cfs.payment_service.entity.Payment;
import com.cfs.payment_service.enums.CurrencyType;
import com.cfs.payment_service.enums.PaymentGateway;
import com.cfs.payment_service.enums.PaymentStatus;
import com.cfs.payment_service.exception.DuplicatePaymentException;
import com.cfs.payment_service.exception.InvalidPaymentException;
import com.cfs.payment_service.exception.PaymentNotFoundException;
import com.cfs.payment_service.mapper.PaymentMapper;
import com.cfs.payment_service.repository.PaymentRepository;
import com.cfs.payment_service.security.LoggedInUserService;
import com.cfs.payment_service.service.PaymentService;
import com.cfs.payment_service.util.PaymentUtil;
import com.razorpay.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final LoggedInUserService loggedInUserService;
    private final PaymentUtil paymentUtil;
    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;
    /**
     * Create Razorpay Order
     * Save Payment in Database
     */
    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {

        log.info("Creating payment for Order Id : {}", request.getOrderId());

        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new DuplicatePaymentException(
                    "Payment already exists for Order Id : " + request.getOrderId()
            );
        }

        Long customerId = loggedInUserService.getCurrentUserId();

        try {

            if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidPaymentException("Invalid payment amount.");
            }

            String paymentReference = paymentUtil.generatePaymentReference();

            JSONObject options = new JSONObject();

            // Razorpay amount should be in paise
            long amountInPaise = request.getAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .longValueExact();

            options.put("amount", amountInPaise);
            options.put("currency", request.getCurrency());
            options.put("receipt", paymentReference);

            Order razorpayOrder = razorpayClient.orders.create(options);

            Payment payment = Payment.builder()
                    .orderId(request.getOrderId())
                    .customerId(customerId)
                    .paymentReference(paymentReference)
                    .razorpayOrderId(razorpayOrder.get("id"))
                    .amount(request.getAmount())
                    .currency(CurrencyType.valueOf(request.getCurrency()))
                    .paymentMethod(request.getPaymentMethod())
                    .paymentGateway(PaymentGateway.RAZORPAY)
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();

            Payment savedPayment = paymentRepository.save(payment);

            PaymentResponse response = paymentMapper.toResponse(savedPayment);

            // Required by React Razorpay Checkout
            response.setRazorpayKey(razorpayKey);
            response.setRazorpayOrderId(savedPayment.getRazorpayOrderId());

            log.info(
                    "Payment created successfully. Payment Reference: {}, Razorpay Order Id: {}",
                    savedPayment.getPaymentReference(),
                    savedPayment.getRazorpayOrderId()
            );

            return response;

        } catch (Exception ex) {

            log.error("Error while creating payment", ex);

            throw new InvalidPaymentException(
                    "Unable to create payment. " + ex.getMessage()
            );
        }
    }
    private void validateDuplicateVerification(Payment payment) {

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {

            throw new InvalidPaymentException(
                    "Payment is already verified.");

        }

    }
    // Remaining methods will be implemented in Package 4B-2

    @Override
    @Transactional
    public PaymentResponse verifyPayment(VerifyPaymentRequest request) {

        log.info("Verifying payment : {}", request.getPaymentReference());

        Payment payment = paymentRepository
                .findByPaymentReference(request.getPaymentReference())
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with reference : "
                                        + request.getPaymentReference()));

        try {

            JSONObject options = new JSONObject();

            options.put("razorpay_order_id", request.getRazorpayOrderId());

            options.put("razorpay_payment_id", request.getRazorpayPaymentId());

            options.put("razorpay_signature", request.getRazorpaySignature());

            /*
             * Official Razorpay Signature Verification
             */
            boolean isValid = Utils.verifyPaymentSignature(
                    options,
                    razorpayKeySecret
            );

            if (!isValid) {

                payment.setPaymentStatus(PaymentStatus.FAILED);

                payment.setFailureReason("Invalid Razorpay Signature");

                paymentRepository.save(payment);

                throw new InvalidPaymentException(
                        "Payment verification failed.");
            }

            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            payment.setRazorpayPaymentId(
                    request.getRazorpayPaymentId());

            payment.setRazorpaySignature(
                    request.getRazorpaySignature());

            payment.setPaidAt(LocalDateTime.now());

            Payment updatedPayment =
                    paymentRepository.save(payment);

            PaymentResponse response =
                    paymentMapper.toResponse(updatedPayment);

            response.setRazorpayKey(razorpayKey);

            log.info(
                    "Payment Verified Successfully : {}",
                    updatedPayment.getPaymentReference());

            return response;

        } catch (RazorpayException ex) {

            log.error("Razorpay Verification Failed", ex);

            throw new InvalidPaymentException(
                    "Unable to verify payment.");

        } catch (Exception ex) {

            log.error("Unexpected Error", ex);

            throw new InvalidPaymentException(
                    ex.getMessage());
        }

    }
    private void validateSuccessfulPayment(Payment payment) {

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {

            throw new InvalidPaymentException(
                    "Payment is not successful.");

        }

    }
    @Override
    @Transactional
    public PaymentResponse refundPayment(RefundPaymentRequest request) {

        log.info("Refund initiated for Payment Id : {}", request.getPaymentId());

        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with Id : " + request.getPaymentId()));

        Long customerId = loggedInUserService.getCurrentUserId();

        if (!payment.getCustomerId().equals(customerId)) {
            throw new InvalidPaymentException(
                    "You are not authorized to refund this payment.");
        }

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new InvalidPaymentException(
                    "Only successful payments can be refunded.");
        }

        try {

            JSONObject refundRequest = new JSONObject();

            long refundAmount = request.getRefundAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .longValueExact();

            refundRequest.put("amount", refundAmount);

            Refund refund = razorpayClient.payments
                    .refund(payment.getRazorpayPaymentId(), refundRequest);

            payment.setPaymentStatus(PaymentStatus.REFUNDED);

            payment.setRemarks(request.getRemarks());

            Payment updatedPayment = paymentRepository.save(payment);

            PaymentResponse response = paymentMapper.toResponse(updatedPayment);

            response.setRazorpayKey(razorpayKey);

            log.info(
                    "Refund successful. Razorpay Refund Id : {}",
                    refund);

            return response;

        } catch (RazorpayException ex) {

            log.error("Refund failed", ex);

            throw new InvalidPaymentException(
                    "Unable to process refund.");

        } catch (Exception ex) {

            log.error("Unexpected refund error", ex);

            throw new InvalidPaymentException(
                    ex.getMessage());

        }

    }

    @Override
    @Transactional
    public PaymentResponse cancelPayment(Long paymentId) {

        log.info("Cancelling payment : {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with Id : " + paymentId));

        Long customerId = loggedInUserService.getCurrentUserId();

        if (!payment.getCustomerId().equals(customerId)) {
            throw new InvalidPaymentException(
                    "You are not authorized to cancel this payment.");
        }

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new InvalidPaymentException(
                    "Successful payment cannot be cancelled. Please initiate a refund.");
        }

        if (payment.getPaymentStatus() == PaymentStatus.REFUNDED) {
            throw new InvalidPaymentException(
                    "Payment has already been refunded.");
        }

        if (payment.getPaymentStatus() == PaymentStatus.CANCELLED) {
            return paymentMapper.toResponse(payment);
        }

        payment.setPaymentStatus(PaymentStatus.CANCELLED);

        payment.setRemarks("Payment cancelled by customer.");

        Payment updatedPayment = paymentRepository.save(payment);

        PaymentResponse response = paymentMapper.toResponse(updatedPayment);

        response.setRazorpayKey(razorpayKey);

        log.info("Payment cancelled successfully : {}",
                updatedPayment.getPaymentReference());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long paymentId) {

        log.info("Fetching payment by Id : {}", paymentId);

        Long customerId = loggedInUserService.getCurrentUserId();

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with Id : " + paymentId));

        if (!payment.getCustomerId().equals(customerId)) {
            throw new InvalidPaymentException(
                    "You are not authorized to access this payment.");
        }

        PaymentResponse response = paymentMapper.toResponse(payment);

        response.setRazorpayKey(razorpayKey);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {

        log.info("Fetching payment for Order Id : {}", orderId);

        Long customerId = loggedInUserService.getCurrentUserId();

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found for Order Id : " + orderId));

        if (!payment.getCustomerId().equals(customerId)) {
            throw new InvalidPaymentException(
                    "You are not authorized to access this payment.");
        }

        PaymentResponse response = paymentMapper.toResponse(payment);

        response.setRazorpayKey(razorpayKey);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentHistoryResponse> getCurrentUserPayments() {

        Long customerId = loggedInUserService.getCurrentUserId();

        log.info("Fetching payment history for Customer : {}", customerId);

        List<Payment> payments =
                paymentRepository.findByCustomerId(customerId);

        return paymentMapper.toHistoryResponseList(payments);
    }

    private Payment getPaymentByReference(String paymentReference) {

        return paymentRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with Reference : "
                                        + paymentReference));

    }
    private Payment getPayment(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with Id : "
                                        + paymentId));

    }

    private Payment savePayment(Payment payment) {

        return paymentRepository.save(payment);

    }
    private void validateOwnership(Payment payment) {

        Long customerId = loggedInUserService.getCurrentUserId();

        if (!payment.getCustomerId().equals(customerId)) {

            throw new InvalidPaymentException(
                    "You are not authorized to access this payment.");

        }

    }
}