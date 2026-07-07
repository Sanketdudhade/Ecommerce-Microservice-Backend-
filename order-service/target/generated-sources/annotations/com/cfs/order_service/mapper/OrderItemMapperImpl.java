package com.cfs.order_service.mapper;

import com.cfs.order_service.dto.response.OrderItemResponse;
import com.cfs.order_service.entity.OrderItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-06T22:20:44+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemResponse toResponse(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemResponse.OrderItemResponseBuilder orderItemResponse = OrderItemResponse.builder();

        orderItemResponse.productId( orderItem.getProductId() );
        orderItemResponse.productName( orderItem.getProductName() );
        orderItemResponse.imageUrl( orderItem.getImageUrl() );
        orderItemResponse.quantity( orderItem.getQuantity() );
        orderItemResponse.unitPrice( orderItem.getUnitPrice() );
        orderItemResponse.totalPrice( orderItem.getTotalPrice() );

        return orderItemResponse.build();
    }

    @Override
    public List<OrderItemResponse> toResponseList(List<OrderItem> orderItems) {
        if ( orderItems == null ) {
            return null;
        }

        List<OrderItemResponse> list = new ArrayList<OrderItemResponse>( orderItems.size() );
        for ( OrderItem orderItem : orderItems ) {
            list.add( toResponse( orderItem ) );
        }

        return list;
    }
}
