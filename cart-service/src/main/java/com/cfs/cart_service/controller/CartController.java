package com.cfs.cart_service.controller;

import com.cfs.cart_service.Constant.HeaderConstants;
import com.cfs.cart_service.dto.request.AddToCartRequest;
import com.cfs.cart_service.dto.request.UpdateCartRequest;
import com.cfs.cart_service.dto.response.CartResponse;
import com.cfs.cart_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Add product to cart
     */
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(
            @RequestHeader(HeaderConstants.USER_ID) Long userId,
            @Valid @RequestBody AddToCartRequest request) {

        CartResponse response = cartService.addToCart(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get logged-in user's cart
     */
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            @RequestHeader(HeaderConstants.USER_ID) Long userId) {

        CartResponse response = cartService.getCart(userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Update quantity of a product in cart
     */
    @PutMapping("/items")
    public ResponseEntity<CartResponse> updateCartItem(
            @RequestHeader(HeaderConstants.USER_ID) Long userId,
            @Valid @RequestBody UpdateCartRequest request) {

        CartResponse response =
                cartService.updateCartItem(userId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Remove single product from cart
     */
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @RequestHeader(HeaderConstants.USER_ID) Long userId,
            @PathVariable Long productId) {

        cartService.removeItem(userId, productId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Clear complete cart
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @RequestHeader(HeaderConstants.USER_ID) Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.noContent().build();
    }
}