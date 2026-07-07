package com.cfs.product_Service.repository;



import com.cfs.product_Service.entity.Category;
import com.cfs.product_Service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByBrand(String brand);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByActiveTrue();

}