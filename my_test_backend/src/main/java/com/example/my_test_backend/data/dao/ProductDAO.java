package com.example.my_test_backend.data.dao;

import com.example.my_test_backend.data.entity.Product;
import com.example.my_test_backend.data.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDAO {
    private final ProductRepository productRepository;

    // 전체 상품 목록 가져오기
    public List<Product> getAllProducts() {
        // DB에 저장된 모든 상품 리스트를 가져옴
        return this.productRepository.findAll();
    }

    // 특정 ID를 가진 상품 가져오기
    public Product getProductById(Integer id) {
        // DB에서 해당 ID의 상품을 찾음 (Optional 타입으로 감싸져 있음)
        Optional<Product> product = this.productRepository.findById(id);

        // 상품이 존재하면 반환, 없으면 null 반환
        return product.orElse(null);
    }

    // 새로운 상품 저장하기
    public Product saveProduct(String title, String imagesrc, Integer price) {
        // Product 객체를 생성 (빌더 패턴 사용)
        Product product = Product.builder()
                .title(title)           // 제목 설정
                .imagesrc(imagesrc)     // 이미지 경로 설정
                .price(price)           // 가격 설정
                .build();

        // 생성한 상품을 DB에 저장하고 반환
        return productRepository.save(product);
    }

    // 상품 ID로 삭제하기
    public boolean deleteProductById(Integer id) {
        // 해당 ID의 상품이 존재하는지 확인
        if (this.productRepository.existsById(id)) {
            // 존재하면 삭제하고 true 반환
            this.productRepository.deleteById(id);
            return true;
        }

        // 존재하지 않으면 false 반환
        return false;
    }

    // 상품 ID로 가격만 업데이트하기
    public Product updateProductById(Integer id, Integer price) {
        // 해당 ID의 상품을 가져옴
        Optional<Product> product = this.productRepository.findById(id);

        // 상품이 존재하면 수정 진행
        Product updatedProduct = product.orElse(null);
        if (updatedProduct != null) {
            updatedProduct.setPrice(price); // 가격 수정
            // 수정된 내용을 DB에 저장
            return productRepository.save(updatedProduct);
        }

        // 상품이 존재하지 않으면 null 반환
        return null;
    }

    //✅ 이 DAO 클래스의 의도성 요약
    //메서드 이름	목적 설명
    //getAllProducts()	모든 상품을 DB에서 가져온다
    //getProductById(id)	특정 상품 ID로 상품 1개를 가져온다
    //saveProduct(...)	새 상품을 생성하여 DB에 저장한다
    //deleteProductById(id)	해당 ID의 상품을 삭제한다
    //updateProductById(id, price)	해당 상품의 가격만 수정하고 저장한다

}
