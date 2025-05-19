package com.example.my_test_backend.controller;

import com.example.my_test_backend.data.dto.ProductDTO;
import com.example.my_test_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController는 RESTful API를 통해 제품 관련 요청을 처리하는 컨트롤러입니다.
 * ProductService와 상호작용하여 CRUD 작업을 수행합니다.
 */
@RestController // Spring에서 REST API 컨트롤러로 등록
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자를 자동 생성
public class ProductController {
    private final ProductService productService; // 서비스 레이어와의 연결

    /**
     * 모든 제품 목록을 조회합니다.
     *
     * @return ResponseEntity<List < ProductDTO>> 제품 DTO 리스트와 HTTP 상태 200
     */
    @GetMapping(value = "/product-list")
    public ResponseEntity<List<ProductDTO>> getProductList() {
        List<ProductDTO> productDTOList = this.productService.getAllProducts(); // 서비스에서 모든 제품 조회
        return ResponseEntity.ok(productDTOList); // 200 OK와 함께 DTO 리스트 반환
    }

    /**
     * 새로운 제품을 추가합니다.
     *
     * @param productDTO 추가할 제품 정보 (JSON 요청 본문)
     * @return ResponseEntity<ProductDTO> 저장된 제품 DTO와 HTTP 상태 201
     */
    @PostMapping(value = "/new-product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = this.productService.saveProduct(productDTO); // 서비스를 통해 제품 저장
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO); // 201 Created와 함께 DTO 반환
    }

    /**
     * 기존 제품의 정보를 업데이트합니다.
     *
     * @param productDTO 업데이트할 제품 정보 (JSON 요청 본문)
     * @return ResponseEntity<ProductDTO> 업데이트된 DTO와 HTTP 상태 200, 실패 시 204
     */
    @PutMapping(value = "/product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = this.productService.updateProduct(productDTO); // 서비스를 통해 제품 업데이트
        if (updatedProductDTO != null) {
            return ResponseEntity.ok(updatedProductDTO); // 200 OK와 함께 DTO 반환
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content, 업데이트 실패 시
    }

    /**
     * 주어진 ID로 제품을 삭제합니다.
     *
     * @param id 삭제할 제품 ID (경로 변수)
     * @return ResponseEntity<String> 삭제 성공/실패 메시지와 HTTP 상태
     */
    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        boolean deleted = this.productService.deleteProduct(id); // 서비스를 통해 제품 삭제
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("상품 삭제 성공"); // 200 OK와 성공 메시지
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제 상품 검색 실패"); // 404 Not Found와 실패 메시지
    }
}

//역할: 클라이언트 요청을 받고 응답을 반환
//주로 REST API 경로(/api/product 등)를 정의하고, @GetMapping, @PostMapping 등을 사용
//서비스 레이어(ProductService)를 호출해서 실제 작업을 처리함