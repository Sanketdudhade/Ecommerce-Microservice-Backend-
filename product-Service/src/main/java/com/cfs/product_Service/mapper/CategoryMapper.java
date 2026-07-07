package com.cfs.product_Service.mapper;



import com.cfs.product_Service.dto.request.CreateCategoryRequest;
import com.cfs.product_Service.dto.response.CategoryResponse;
import com.cfs.product_Service.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CreateCategoryRequest request);

    CategoryResponse toResponse(Category category);

}
