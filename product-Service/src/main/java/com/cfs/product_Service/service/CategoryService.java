package com.cfs.product_Service.service;



import com.cfs.product_Service.dto.request.CreateCategoryRequest;
import com.cfs.product_Service.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    void deleteCategory(Long id);
}
