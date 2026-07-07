package com.cfs.payment_service.client;

import com.cfs.payment_service.dto.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @GetMapping("/api/orders/{id}")
    OrderResponse getOrderById(@PathVariable Long id);

    @PutMapping("/api/orders/{id}/payment-success")
    void paymentSuccess(@PathVariable Long id);

    @PutMapping("/api/orders/{id}/payment-failed")
    void paymentFailed(@PathVariable Long id);
}