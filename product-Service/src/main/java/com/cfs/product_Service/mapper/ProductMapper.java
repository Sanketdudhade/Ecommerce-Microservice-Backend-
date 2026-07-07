package com.cfs.product_Service.mapper;



import com.cfs.product_Service.dto.request.CreateProductRequest;
import com.cfs.product_Service.dto.response.ProductResponse;
import com.cfs.product_Service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "category", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);
}