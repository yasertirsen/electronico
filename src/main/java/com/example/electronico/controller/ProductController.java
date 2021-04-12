package com.example.electronico.controller;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.model.Product;
import com.example.electronico.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public Product add(@RequestBody Product product) {
        return productService.add(product);
    }

    @GetMapping("/get/{productId}")
    public Product get(@PathVariable Long productId) throws NotFoundException {
        return productService.get(productId);
    }

    @GetMapping("/get/all")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PutMapping("/update")
    public Product update(@RequestBody Product product) throws NotFoundException {
        return productService.update(product);
    }

    @PutMapping("/delete/{productId}")
    public ResponseEntity<String> delete(@PathVariable Long productId) throws NotFoundException {
        return productService.delete(productId);
    }

    @PostMapping( "/upload-image/{productId}")
    public Product uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long productId) throws IOException, NotFoundException {
        return productService.uploadImage(file, productId);
    }

    @GetMapping("/searchByTitle/{title}")
    public List<Product> searchByTitle(@PathVariable String title) {
        return productService.searchByTitle(title);
    }

    @GetMapping("/searchByCategory/{category}")
    public List<Product> searchByCategory(@PathVariable String category) {
        return productService.searchByCategory(category);
    }

    @GetMapping("/searchByManufacturer/{manufacturer}")
    public List<Product> searchByManufacturer(@PathVariable String manufacturer) {
        return productService.searchByManufacturer(manufacturer);
    }

    @GetMapping("/rating/{productId}")
    public double getRating(@PathVariable Long productId) throws NotFoundException {
        return productService.getRating(productId);
    }
}
