package com.cfs.cart_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemResponse {

    private Long productId;

    private String productName;

    private String productImage;

    private BigDecimal  price;

    private Integer quantity;

    private BigDecimal subtotal;
}