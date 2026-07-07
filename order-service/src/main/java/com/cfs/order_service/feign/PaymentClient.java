package com.cfs.order_service.feign;

import com.cfs.order_service.dto.feign.PaymentRequest;
import com.cfs.order_service.dto.feign.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/api/payments")
    PaymentResponse createPayment(@RequestBody PaymentRequest request);

}