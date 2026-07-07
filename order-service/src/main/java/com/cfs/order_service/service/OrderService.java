package com.cfs.order_service.service;

import com.cfs.order_service.dto.request.CreateOrderRequest;
import com.cfs.order_service.dto.request.UpdateOrderStatusRequest;
import com.cfs.order_service.dto.response.OrderResponse;
import com.cfs.order_service.dto.response.OrderSummaryResponse;
import com.cfs.order_service.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(CreateOrderRequest request);

    OrderResponse getOrderById(Long orderId);

    List<Order> getMyOrders();

    List<OrderSummaryResponse> getAllOrders();

    OrderResponse updateOrderStatus(Long orderId,
                                    UpdateOrderStatusRequest request);

    void cancelOrder(Long orderId);

}