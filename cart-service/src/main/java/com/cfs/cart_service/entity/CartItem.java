package com.cfs.cart_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product ID from Product Service
     */
    @Column(nullable = false)
    private Long productId;

    /**
     * Product details snapshot
     */
    @Column(nullable = false)
    private String productName;

    private String productImage;

    /**
     * Price of one unit
     */
    @Column(nullable = false)
    private BigDecimal  price;

    /**
     * Quantity added by user
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * price * quantity
     */
    @Column(nullable = false)
    private BigDecimal subtotal;

    /**
     * Many CartItems belong to one Cart
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
}