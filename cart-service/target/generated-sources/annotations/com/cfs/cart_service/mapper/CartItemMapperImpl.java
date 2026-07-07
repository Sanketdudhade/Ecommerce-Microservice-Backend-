package com.cfs.cart_service.mapper;

import com.cfs.cart_service.dto.response.CartItemResponse;
import com.cfs.cart_service.entity.CartItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-06T11:21:33+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemResponse.CartItemResponseBuilder cartItemResponse = CartItemResponse.builder();

        cartItemResponse.productId( cartItem.getProductId() );
        cartItemResponse.productName( cartItem.getProductName() );
        cartItemResponse.productImage( cartItem.getProductImage() );
        cartItemResponse.price( cartItem.getPrice() );
        cartItemResponse.quantity( cartItem.getQuantity() );
        cartItemResponse.subtotal( cartItem.getSubtotal() );

        return cartItemResponse.build();
    }

    @Override
    public List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems) {
        if ( cartItems == null ) {
            return null;
        }

        List<CartItemResponse> list = new ArrayList<CartItemResponse>( cartItems.size() );
        for ( CartItem cartItem : cartItems ) {
            list.add( toCartItemResponse( cartItem ) );
        }

        return list;
    }
}
