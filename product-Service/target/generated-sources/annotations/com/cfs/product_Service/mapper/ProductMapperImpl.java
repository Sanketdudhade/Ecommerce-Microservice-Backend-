package com.cfs.product_Service.mapper;

import com.cfs.product_Service.dto.request.CreateProductRequest;
import com.cfs.product_Service.dto.response.ProductResponse;
import com.cfs.product_Service.entity.Category;
import com.cfs.product_Service.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-05T20:51:12+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(CreateProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );
        product.stock( request.getStock() );
        product.brand( request.getBrand() );
        product.imageUrl( request.getImageUrl() );

        product.active( true );

        return product.build();
    }

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.categoryName( productCategoryName( product ) );
        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );
        productResponse.stock( product.getStock() );
        productResponse.brand( product.getBrand() );
        productResponse.imageUrl( product.getImageUrl() );
        productResponse.active( product.getActive() );

        return productResponse.build();
    }

    private String productCategoryName(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getName();
    }
}
