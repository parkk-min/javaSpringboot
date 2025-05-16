package com.example.my_test_backend.service;

import com.example.my_test_backend.data.dao.ProductDAO;
import com.example.my_test_backend.data.dto.ProductDTO;
import com.example.my_test_backend.data.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO; // DB 접근을 위한 DAO 객체

    /**
     * 모든 제품 목록을 조회합니다.
     * @return List<ProductDTO> 모든 제품의 DTO 리스트
     */
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>(); // DTO 리스트 초기화
        List<Product> products = this.productDAO.getAllProducts(); // DAO에서 모든 제품 조회
        for (Product product : products) { // 각 제품을 DTO로 변환
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .imagesrc(product.getImagesrc())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build();
            productDTOList.add(productDTO); // 변환된 DTO를 리스트에 추가
        }
        return productDTOList; // DTO 리스트 반환
    }

    /**
     * 주어진 ID로 특정 제품을 조회합니다.
     * @param id 제품 ID
     * @return ProductDTO 조회된 제품의 DTO, 없으면 null 반환
     */
    public ProductDTO getProductById(Integer id) {
        Product product = this.productDAO.getProductById(id); // DAO에서 ID로 제품 조회
        if (product != null) { // 제품이 존재하면
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .imagesrc(product.getImagesrc())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build();
            return productDTO; // DTO로 변환 후 반환
        }
        return null; // 제품이 없으면 null 반환
    }

    /**
     * 새로운 제품을 저장합니다.
     * @param productDTO 저장할 제품 정보 (DTO)
     * @return ProductDTO 저장된 제품의 DTO
     */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // DAO를 통해 제품 저장, 이미지 URL은 임시로 더미 이미지 사용
        Product product = this.productDAO.saveProduct(productDTO.getTitle(), "https://dummyimage.com/200x200/00f/fff.jpg&text=product", productDTO.getPrice());
        ProductDTO saveproductDTO = ProductDTO.builder()
                .id(product.getId())
                .imagesrc(product.getImagesrc())
                .title(product.getTitle())
                .price(product.getPrice())
                .build();
        return saveproductDTO; // 저장된 제품 정보를 DTO로 반환
    }

    /**
     * 주어진 ID의 제품 가격을 업데이트합니다.
     * @param productDTO 업데이트할 제품 정보 (DTO)
     * @return ProductDTO 업데이트된 제품의 DTO, 없으면 null 반환
     */
    public ProductDTO updateProduct(ProductDTO productDTO) {
        // DAO를 통해 제품 가격 업데이트
        Product product = this.productDAO.updateProductById(productDTO.getId(), productDTO.getPrice());
        if (product != null) { // 업데이트 성공 시
            ProductDTO updateProduct = ProductDTO.builder()
                    .id(product.getId())
                    .imagesrc(product.getImagesrc())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build();
            return updateProduct; // DTO로 변환 후 반환
        }
        return null; // 업데이트 실패 시 null 반환
    }

    /**
     * 주어진 ID로 제품을 삭제합니다.
     * @param id 삭제할 제품 ID
     * @return boolean 삭제 성공 여부
     */
    public boolean deleteProduct(Integer id) {
        return this.productDAO.deleteProductById(id); // DAO를 통해 삭제 후 결과 반환
    }
}