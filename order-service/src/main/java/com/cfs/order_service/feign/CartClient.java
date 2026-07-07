package com.cfs.order_service.feign;

import com.cfs.order_service.dto.feign.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

    @GetMapping("/api/cart")
    CartResponse getMyCart();

    @DeleteMapping("/api/cart/clear")
    void clearCart();
}