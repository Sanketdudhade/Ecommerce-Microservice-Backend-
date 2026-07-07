package com.cfs.cart_service.service;

import com.cfs.cart_service.dto.request.AddToCartRequest;
import com.cfs.cart_service.dto.request.UpdateCartRequest;
import com.cfs.cart_service.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(Long userId, AddToCartRequest request);

    CartResponse getCart(Long userId);

    CartResponse updateCartItem(Long userId, UpdateCartRequest request);

    void removeItem(Long userId, Long productId);

    void clearCart(Long userId);
}