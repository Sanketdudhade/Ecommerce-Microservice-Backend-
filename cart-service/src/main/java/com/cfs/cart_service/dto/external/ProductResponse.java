package com.cfs.cart_service.dto.external;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String brand;

    private String imageUrl;

    private Boolean active;

    private String categoryName;
}