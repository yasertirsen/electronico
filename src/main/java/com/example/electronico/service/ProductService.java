package com.example.electronico.service;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.model.Product;
import com.example.electronico.model.Review;
import com.example.electronico.repository.ProductRepository;
import com.example.electronico.service.pattern.CrudTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService extends CrudTemplate<Product> {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    public Product uploadImage(MultipartFile file, Long productId) throws NotFoundException, IOException {
        Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);
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

    public double getRating(Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        double rating = 0.0;
        for(Review review : product.getReviews()) {
            rating+=review.getRating();
        }
        return Math.round((rating/product.getReviews().size()) * 100.0) / 100.0;
    }
}
