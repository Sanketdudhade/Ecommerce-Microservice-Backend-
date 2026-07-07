package com.cfs.cart_service.mapper;

import com.cfs.cart_service.dto.response.CartItemResponse;
import com.cfs.cart_service.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemResponse toCartItemResponse(CartItem cartItem);

    List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems);
}