package com.cfs.order_service.repository;

import com.cfs.order_service.entity.Order;
import com.cfs.order_service.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByCustomerId(Long customerId);

    List<Order>findByOrderStatus(OrderStatus status);

    boolean existsByOrderNumber(String orderNumber);

}