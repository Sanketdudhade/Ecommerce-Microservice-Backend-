package com.cfs.product_Service.service;


import com.cfs.product_Service.dto.request.CreateProductRequest;
import com.cfs.product_Service.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);
    List<ProductResponse> getActiveProducts();

    ProductResponse updateProduct(Long id,
                                  CreateProductRequest request);

    void deleteProduct(Long id);
}