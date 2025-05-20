package com.example.my_test_backend.controller;

import com.example.my_test_backend.data.dto.ProductDTO;
import com.example.my_test_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/product-list")
    public ResponseEntity<List<ProductDTO>> getProductList() {
        List<ProductDTO> productDTOList = this.productService.getAllProducts();
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping(value = "/new-product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = this.productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO);
    }

    @PutMapping(value = "/product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = this.productService.updateProductById(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProductDTO);
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        this.productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body("상품 삭제 성공");
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<ProductDTO> getException(@PathVariable Integer id) {
      ProductDTO productDTO = this.productService.getProductById(id);
      return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }


}
