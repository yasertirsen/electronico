package com.example.electronico.service;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Product;
import com.example.electronico.repository.ProductRepository;
import com.example.electronico.service.interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) throws ProductNotFoundException {
        if(productRepository.existsById(product.getProductId()))
            return productRepository.save(product);
        throw new ProductNotFoundException("Product to be updated was not found");
    }

    @Override
    public ResponseEntity<String> delete(Long productId) throws ProductNotFoundException {
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return new ResponseEntity<>("Product with id " + productId + " has been deleted", HttpStatus.OK);
        }
            throw new ProductNotFoundException("Product to be deleted was not found");
    }

    @Override
    public Product uploadImage(MultipartFile file, Long productId) throws IOException, UserNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(UserNotFoundException::new);
        product.setImage(file.getBytes());
        return productRepository.save(product);
    }
}
