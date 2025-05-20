package com.example.my_test_backend.service;

import com.example.my_test_backend.data.dao.ProductDAO;
import com.example.my_test_backend.data.dto.ProductDTO;
import com.example.my_test_backend.data.entity.Product;
import com.example.my_test_backend.exception.MyException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> products = this.productDAO.getAllProducts();
        for (Product product : products) {
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .imagesrc(product.getImagesrc())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build();
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    public ProductDTO getProductById(Integer id) {
        Product product = this.productDAO.getProductById(id);
        if (product == null) {
            throw new MyException("현재 ID의 상품이 존재하지 않습니다.");
        }
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .imagesrc(product.getImagesrc())
                .title(product.getTitle())
                .price(product.getPrice())
                .build();
        return productDTO;
    }

public ProductDTO saveProduct(ProductDTO productDTO) {
    Product product = this.productDAO.saveProduct(productDTO.getTitle(), "https://dummyimage.com/200x200/00f/fff.jpg&text=product",
            productDTO.getPrice(), LocalDateTime.now(), "생성");
    ProductDTO saveproductDTO = ProductDTO.builder()
            .id(product.getId())
            .imagesrc(product.getImagesrc())
            .title(product.getTitle())
            .price(product.getPrice())
            .build();
    return saveproductDTO;
}

public ProductDTO updateProductById(ProductDTO productDTO) {
    Product product = this.productDAO.updateProductById(productDTO.getId(),
            productDTO.getPrice(), LocalDateTime.now(), "수정");
    if (product == null) {
        throw new EntityNotFoundException("값을 수정하려는 상품이 존재하지 않습니다.");
    }
    ProductDTO updateproductDTO = ProductDTO.builder()
            .id(product.getId())
            .imagesrc(product.getImagesrc())
            .title(product.getTitle())
            .price(product.getPrice())
            .build();
    return updateproductDTO;
}

public void deleteProductById(Integer id) {
    boolean result = this.productDAO.deleteProductById(id);
    if (!result) {
        throw new EntityNotFoundException("삭제하고자 하는 상품이 존재하지 않습니다.");
    }
}


}

