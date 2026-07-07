package com.cfs.order_service.mapper;

import com.cfs.order_service.dto.response.OrderItemResponse;
import com.cfs.order_service.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemResponse toResponse(OrderItem orderItem);

    List<OrderItemResponse> toResponseList(List<OrderItem> orderItems);

}