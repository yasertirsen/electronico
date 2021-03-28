package com.example.electronico.controller;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Product;
import com.example.electronico.service.interfaces.ProductService;
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
    public Product get(@PathVariable Long productId) throws ProductNotFoundException {
        return productService.get(productId);
    }

    @GetMapping("/get/all")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PutMapping("/update")
    public Product update(@RequestBody Product product) throws ProductNotFoundException {
        return productService.update(product);
    }

    @PutMapping("/delete/{productId}")
    public ResponseEntity<String> delete(@PathVariable Long productId) throws ProductNotFoundException {
        return productService.delete(productId);
    }

    @PostMapping(value = "/upload-image/{productId}")
    public Product uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long productId) throws IOException, UserNotFoundException {
        return productService.uploadImage(file, productId);
    }
}