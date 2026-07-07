package com.cfs.order_service.controller;

import com.cfs.order_service.dto.request.CreateOrderRequest;
import com.cfs.order_service.dto.request.UpdateOrderStatusRequest;
import com.cfs.order_service.dto.response.OrderResponse;
import com.cfs.order_service.dto.response.OrderSummaryResponse;
import com.cfs.order_service.entity.Order;
import com.cfs.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse response = orderService.placeOrder(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }


    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders() {

        return ResponseEntity.ok(orderService.getMyOrders());
    }


    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> getAllOrders() {

        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, request));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.ok("Order cancelled successfully.");
    }

}