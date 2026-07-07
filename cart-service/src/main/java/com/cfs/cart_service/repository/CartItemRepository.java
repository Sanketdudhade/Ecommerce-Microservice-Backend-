package com.cfs.cart_service.repository;

import com.cfs.cart_service.entity.Cart;
import com.cfs.cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProductId(Cart cart, Long productId);

    void deleteByCartAndProductId(Cart cart, Long productId);

    void deleteAllByCart(Cart cart);
}
