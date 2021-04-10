package com.example.electronico.service;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Product;
import com.example.electronico.repository.ProductRepository;
import com.example.electronico.service.interfaces.CrudStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService implements CrudStrategy<Product> {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long productId) throws NotFoundException {
        return productRepository.findById(productId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) throws NotFoundException {
        if(productRepository.existsById(product.getProductId()))
            return productRepository.save(product);
        throw new NotFoundException("Product to be updated was not found");
    }

    @Override
    public ResponseEntity<String> delete(Long productId) throws NotFoundException {
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return new ResponseEntity<>("Product with id " + productId + " has been deleted", HttpStatus.OK);
        }
        throw new NotFoundException("Product to be deleted was not found");
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
