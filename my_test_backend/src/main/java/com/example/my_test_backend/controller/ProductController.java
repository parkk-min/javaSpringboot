package com.example.my_test_backend.controller;

import com.example.my_test_backend.data.Product;
import com.example.my_test_backend.data.ProductDTO;
import com.example.my_test_backend.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // JSON 응답을 하는 API 컨트롤러
@RequiredArgsConstructor // final 필드들을 자동 생성자 주입
public class ProductController {
    private final ProductRepository productRepository;

    // 상 전체 리스트를 가져오는 GET 요청
    @GetMapping(value = "/product-list")
    public List<Product> getProductList() {
        // 데이터베이스에 있는 모든 상품을 가져와서 리턴
        return this.productRepository.findAll();
    }

    @PostMapping(value = "/new-product")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        // 빈 상품 객체를 하나 만들고
        Product product = new Product();

        // 받은 DTO의 값들을 상품에 복사
        product.setPrice(productDTO.getPrice());
        product.setTitle(productDTO.getTitle());
        product.setImagesrc(productDTO.getImagesrc());

        // 저장된 상품 객체를 saveProduct에 저장
        Product saveProduct = this.productRepository.save(product);

        // 다시 DTO로 바꿔서 프론트에 전달
        ProductDTO saveProductDTO = new ProductDTO();
        saveProductDTO.setTitle(saveProduct.getTitle());
        saveProductDTO.setPrice(saveProduct.getPrice());
        saveProductDTO.setImagesrc(saveProduct.getImagesrc());

        return saveProductDTO;
    }

    @PutMapping(value = "/product")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        // 수정하려는 상품이 있는지 찾기 (id 기준)
        Optional<Product> productOptional = this.productRepository.findById(productDTO.getId());

        // 상품이 있으면
        if (productOptional.isPresent()) {
            Product product = productOptional.get();  // 상품 꺼내기
            product.setPrice(productDTO.getPrice()); // 가격만 수정

            // 다시 저장
            Product updateProduct = this.productRepository.save(product);

            // 수정된 상품을 DTO로 바꿔서 보내기
            ProductDTO updateProductDTO = new ProductDTO();
            updateProductDTO.setId(updateProduct.getId());
            updateProductDTO.setTitle(updateProduct.getTitle());
            updateProductDTO.setPrice(updateProduct.getPrice());
            updateProductDTO.setImagesrc(updateProduct.getImagesrc());

            return updateProductDTO;
        }

        return null; // 상품이 없으면 null 리턴
    }

    // [DELETE] 요청: /product/1 이런 형식
    @DeleteMapping(value = "/product/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        // 해당 ID가 존재하면
        if (this.productRepository.existsById(id)) { // 삭제 실행
            this.productRepository.deleteById(id); // 문자열로 결과 반환
            return "Product deleted successfully";
        }
        return "Product not found"; // 존재하지 않을 경우 메시지 반환
    }
}
