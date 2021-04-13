package com.example.electronico.service.pattern.proxy;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product uploadImage(MultipartFile file, Long productId) throws NotFoundException, IOException;
    List<Product> searchByTitle(String title);
    List<Product> searchByCategory(String category);
    List<Product> searchByManufacturer(String manufacturer);
    double getRating(Long productId) throws NotFoundException;
}
