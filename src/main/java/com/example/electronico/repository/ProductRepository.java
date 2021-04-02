package com.example.electronico.repository;

import com.example.electronico.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleIgnoreCaseContaining(String title);
    List<Product> findByCategoryIgnoreCaseContaining(String category);
    List<Product> findByManufacturerLike(String manufacturer);
}
