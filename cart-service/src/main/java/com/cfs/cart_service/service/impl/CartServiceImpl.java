package com.cfs.cart_service.service.impl;

import com.cfs.cart_service.client.ProductClient;
import com.cfs.cart_service.dto.external.ProductResponse;
import com.cfs.cart_service.dto.request.AddToCartRequest;
import com.cfs.cart_service.dto.request.UpdateCartRequest;
import com.cfs.cart_service.dto.response.CartResponse;
import com.cfs.cart_service.entity.Cart;
import com.cfs.cart_service.entity.CartItem;
import com.cfs.cart_service.mapper.CartMapper;
import com.cfs.cart_service.repository.CartItemRepository;
import com.cfs.cart_service.repository.CartRepository;
import com.cfs.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final ProductClient productClient;

    @Override
    public CartResponse addToCart(Long userId, AddToCartRequest request) {

        Cart cart = getOrCreateCart(userId);

        ProductResponse product = productClient.getProductById(request.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found.");
        }

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new RuntimeException("Product is inactive.");
        }

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock available.");
        }

        CartItem cartItem = cartItemRepository
                .findByCartAndProductId(cart, request.getProductId())
                .orElse(null);

        if (cartItem != null) {

            int updatedQuantity = cartItem.getQuantity() + request.getQuantity();

            if (updatedQuantity > product.getStock()) {
                throw new RuntimeException("Requested quantity exceeds available stock.");
            }

            cartItem.setQuantity(updatedQuantity);
            cartItem.setSubtotal(
                    cartItem.getPrice()
                            .multiply(BigDecimal.valueOf(updatedQuantity))
            );

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .productId(product.getId())
                    .productName(product.getName())
                    .productImage(product.getImageUrl())
                    .price(product.getPrice())
                    .quantity(request.getQuantity())
                    .subtotal(
                            product.getPrice()
                                    .multiply(BigDecimal.valueOf(request.getQuantity()))
                    )
                    .build();

            cart.getItems().add(cartItem);
        }

        recalculateCart(cart);

        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found."));

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(Long userId,
                                       UpdateCartRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found."));

        CartItem cartItem = cartItemRepository
                .findByCartAndProductId(cart, request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found in cart."));

        ProductResponse product = productClient.getProductById(request.getProductId());

        if (request.getQuantity() > product.getStock()) {
            throw new RuntimeException("Insufficient stock.");
        }

        cartItem.setQuantity(request.getQuantity());

        cartItem.setSubtotal(
                cartItem.getPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()))
        );

        recalculateCart(cart);

        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    public void removeItem(Long userId,
                           Long productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found."));

        CartItem item = cartItemRepository
                .findByCartAndProductId(cart, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart."));

        cart.getItems().remove(item);

        cartItemRepository.delete(item);

        recalculateCart(cart);

        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found."));

        cart.getItems().clear();

        cart.setTotalAmount(BigDecimal.ZERO);

        cartItemRepository.deleteAllByCart(cart);

        cartRepository.save(cart);
    }

    // ---------------------- Helper Methods ---------------------- //

    private Cart getOrCreateCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {

                    Cart cart = Cart.builder()
                            .userId(userId)
                            .items(new ArrayList<>())
                            .totalAmount(BigDecimal.ZERO)
                            .build();

                    return cartRepository.save(cart);
                });
    }

    private void recalculateCart(Cart cart) {

        BigDecimal total = cart.getItems()
                .stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
    }
}