package com.cfs.order_service.mapper;

import com.cfs.order_service.dto.response.OrderResponse;
import com.cfs.order_service.dto.response.OrderSummaryResponse;
import com.cfs.order_service.entity.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-06T22:20:44+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public OrderResponse toResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.items( orderItemMapper.toResponseList( order.getOrderItems() ) );
        orderResponse.id( order.getId() );
        orderResponse.orderNumber( order.getOrderNumber() );
        orderResponse.customerId( order.getCustomerId() );
        orderResponse.customerName( order.getCustomerName() );
        orderResponse.customerEmail( order.getCustomerEmail() );
        orderResponse.shippingAddress( order.getShippingAddress() );
        orderResponse.phoneNumber( order.getPhoneNumber() );
        orderResponse.totalAmount( order.getTotalAmount() );
        orderResponse.orderStatus( order.getOrderStatus() );
        orderResponse.paymentStatus( order.getPaymentStatus() );
        orderResponse.createdAt( order.getCreatedAt() );

        return orderResponse.build();
    }

    @Override
    public List<OrderResponse> toResponseList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( orders.size() );
        for ( Order order : orders ) {
            list.add( toResponse( order ) );
        }

        return list;
    }

    @Override
    public OrderSummaryResponse toSummaryResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderSummaryResponse.OrderSummaryResponseBuilder orderSummaryResponse = OrderSummaryResponse.builder();

        orderSummaryResponse.id( order.getId() );
        orderSummaryResponse.orderNumber( order.getOrderNumber() );
        orderSummaryResponse.totalAmount( order.getTotalAmount() );
        orderSummaryResponse.orderStatus( order.getOrderStatus() );
        orderSummaryResponse.paymentStatus( order.getPaymentStatus() );
        orderSummaryResponse.createdAt( order.getCreatedAt() );

        return orderSummaryResponse.build();
    }

    @Override
    public List<OrderSummaryResponse> toSummaryResponseList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderSummaryResponse> list = new ArrayList<OrderSummaryResponse>( orders.size() );
        for ( Order order : orders ) {
            list.add( toSummaryResponse( order ) );
        }

        return list;
    }
}
