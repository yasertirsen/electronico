package com.example.electronico.service;

import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Product;
import com.example.electronico.repository.ProductRepository;
import com.example.electronico.service.pattern.CrudTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService extends CrudTemplate<Product> {

    private final ProductRepository productRepository;

    protected ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    public Product uploadImage(MultipartFile file, Long productId) throws IOException, UserNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(UserNotFoundException::new);
        product.setImage(file.getBytes());
        return productRepository.save(product);
    }

    public List<Product> searchByTitle(String title) {
        return productRepository.findByTitleIgnoreCaseContaining(title);
    }

    public List<Product> searchByCategory(String category) {
        return productRepository.findByCategoryIgnoreCaseContaining(category);
    }

    public List<Product> searchByManufacturer(String manufacturer) {
        return productRepository.findByManufacturerLike(manufacturer);
    }
}
