package com.example.electronico.service.interfaces;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product add(Product product);

    Product get(Long productId) throws ProductNotFoundException;

    List<Product> getAll();

    Product update(Product product) throws ProductNotFoundException;

    ResponseEntity<String> delete(Long productId) throws ProductNotFoundException;

    Product uploadImage(MultipartFile file, Long productId) throws IOException, UserNotFoundException;

    List<Product> searchByTitle(String title);

    List<Product> searchByCategory(String category);

    List<Product> searchByManufacturer(String manufacturer);
}
