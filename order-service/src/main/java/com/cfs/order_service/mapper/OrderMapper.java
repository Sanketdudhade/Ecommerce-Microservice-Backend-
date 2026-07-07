package com.cfs.order_service.mapper;

import com.cfs.order_service.dto.response.OrderResponse;
import com.cfs.order_service.dto.response.OrderSummaryResponse;
import com.cfs.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = OrderItemMapper.class
)
public interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    OrderSummaryResponse toSummaryResponse(Order order);

    List<OrderSummaryResponse> toSummaryResponseList(List<Order> orders);

}