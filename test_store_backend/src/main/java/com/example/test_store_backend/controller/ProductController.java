package com.example.test_store_backend.controller;

import com.example.test_store_backend.data.Product;
import com.example.test_store_backend.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping(value = "product-list")
    public List<Product> getProductList() {
        return this.productRepository.findAll();
    }


}
