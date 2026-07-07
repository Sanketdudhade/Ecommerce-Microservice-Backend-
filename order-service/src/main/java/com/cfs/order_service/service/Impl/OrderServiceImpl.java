package com.cfs.order_service.service.Impl;

import com.cfs.order_service.dto.feign.*;
import com.cfs.order_service.dto.request.CreateOrderRequest;
import com.cfs.order_service.dto.request.UpdateOrderStatusRequest;
import com.cfs.order_service.dto.response.OrderResponse;
import com.cfs.order_service.dto.response.OrderSummaryResponse;
import com.cfs.order_service.entity.Order;
import com.cfs.order_service.entity.OrderItem;
import com.cfs.order_service.enums.OrderStatus;
import com.cfs.order_service.enums.PaymentStatus;
import com.cfs.order_service.feign.CartClient;
import com.cfs.order_service.feign.NotificationClient;
import com.cfs.order_service.feign.PaymentClient;
import com.cfs.order_service.mapper.OrderMapper;
import com.cfs.order_service.repository.OrderRepository;
import com.cfs.order_service.security.JwtUser;
import com.cfs.order_service.security.LoggedInUserService;
import com.cfs.order_service.service.OrderService;
import com.cfs.order_service.util.OrderNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final CartClient cartClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;
    private final LoggedInUserService loggedInUserService;
    private final OrderNumberGenerator orderNumberGenerator;

    @Override
    public OrderResponse placeOrder(CreateOrderRequest request) {

        JwtUser user = loggedInUserService.getCurrentUser();

        Long customerId = user.getId();

        CartResponse cart = cartClient.getMyCart();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .orderNumber(orderNumberGenerator.generateOrderNumber())
                .customerId(customerId)
                .customerName(user.getName())
                .customerEmail(user.getEmail())
                .shippingAddress(request.getShippingAddress())
                .phoneNumber(request.getPhoneNumber())
                .totalAmount(cart.getTotalAmount())
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        List<OrderItem> items = new ArrayList<>();

        for (CartItemResponse cartItem : cart.getItems()) {

            OrderItem item = OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .productName(cartItem.getProductName())
                    .imageUrl(cartItem.getProductImage())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getPrice())
                    .totalPrice(cartItem.getSubtotal())
                    .order(order)
                    .build();

            items.add(item);
        }

        order.setOrderItems(items);

        Order savedOrder = orderRepository.save(order);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderNumber(savedOrder.getOrderNumber())
                .customerId(customerId)
                .amount(savedOrder.getTotalAmount())
                .build();

        PaymentResponse paymentResponse =
                paymentClient.createPayment(paymentRequest);

        if ("SUCCESS".equalsIgnoreCase(paymentResponse.getPaymentStatus())) {

            savedOrder.setPaymentStatus(PaymentStatus.SUCCESS);
            savedOrder.setOrderStatus(OrderStatus.CONFIRMED);

            cartClient.clearCart();

            NotificationRequest notification =
                    NotificationRequest.builder()
                            .email(savedOrder.getCustomerEmail())
                            .customerName(savedOrder.getCustomerName())
                            .orderNumber(savedOrder.getOrderNumber())
                            .message("Your order has been placed successfully.")
                            .build();

            notificationClient.sendOrderConfirmation(notification);

        } else {

            savedOrder.setPaymentStatus(PaymentStatus.FAILED);

        }

        return orderMapper.toResponse(orderRepository.save(savedOrder));

    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.toResponse(order);
    }

    @Override
    public List<Order> getMyOrders() {

        JwtUser user = loggedInUserService.getCurrentUser();

        return orderRepository.findByCustomerId(user.getId());

    }

    @Override
    public List<OrderSummaryResponse> getAllOrders() {

        return orderMapper.toSummaryResponseList(
                orderRepository.findAll()
        );

    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId,
                                           UpdateOrderStatusRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(request.getOrderStatus());

        return orderMapper.toResponse(orderRepository.save(order));

    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

    }

}