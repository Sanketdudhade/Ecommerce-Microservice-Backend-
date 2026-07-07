package com.cfs.cart_service.mapper;

import com.cfs.cart_service.dto.response.CartItemResponse;
import com.cfs.cart_service.dto.response.CartResponse;
import com.cfs.cart_service.entity.Cart;
import com.cfs.cart_service.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring"
)
public interface CartMapper {

    CartItemResponse toCartItemResponse(CartItem cartItem);

    List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems);

    @Mapping(target = "cartId", source = "id")
    @Mapping(target = "items", source = "items")
    CartResponse toCartResponse(Cart cart);
}