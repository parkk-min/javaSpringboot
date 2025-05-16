package com.example.my_test_backend.controller;
import com.example.my_test_backend.data.Product;
import com.example.my_test_backend.data.ProductDTO;
import com.example.my_test_backend.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 이 클래스는 REST API를 처리하는 컨트롤러 클래스임을 나타냅니다. JSON 형식으로 응답을 반환합니다.
@RestController
// final 또는 @NonNull 필드에 대한 생성자를 자동으로 만들어줍니다. 의존성 주입을 쉽게 합니다.
@RequiredArgsConstructor
// ProductController 클래스를 정의합니다. 상품 관련 API 요청을 처리합니다.
public class ProductController {
    // ProductRepository를 final로 선언하여 의존성을 주입받습니다. 데이터베이스 작업을 수행합니다.
    private final ProductRepository productRepository;

    @GetMapping(value = "/product-list")
    public ResponseEntity<List<ProductDTO>> getProductList() {
        // 상품 DTO 목록을 저장할 ArrayList를 생성합니다. 클라이언트에 보낼 데이터입니다.
        List<ProductDTO> productDTOList = new ArrayList<>();
        // 데이터베이스에서 모든 상품을 조회하여 List<Product>로 가져옵니다.
        List<Product> productList = this.productRepository.findAll();
        // 조회된 상품 목록을 하나씩 처리하기 위해 반복문을 사용합니다.
        for (Product product : productList) {
            // Product 엔티티를 ProductDTO로 변환합니다. 빌더 패턴을 사용해 객체를 생성합니다.
            ProductDTO productDTO = ProductDTO.builder()
                    // 상품의 ID를 DTO에 설정합니다.
                    .id(product.getId())
                    // 상품의 가격을 DTO에 설정합니다.
                    .price(product.getPrice())
                    // 상품의 제목을 DTO에 설정합니다.
                    .title(product.getTitle())
                    // 상품의 이미지 URL을 DTO에 설정합니다.
                    .imagesrc(product.getImagesrc())
                    // 빌더로 설정한 값을 바탕으로 ProductDTO 객체를 생성합니다.
                    .build();
            // 변환된 ProductDTO를 목록에 추가합니다.
            productDTOList.add(productDTO);
        }
        // HTTP 상태 코드 200(OK)와 함께 상품 DTO 목록을 응답으로 반환합니다.
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping(value = "/new-product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        // 새로운 Product 엔티티를 생성합니다. 빌더 패턴을 사용해 값을 설정합니다.
        Product product = Product.builder()
                // 요청 DTO의 제목을 Product 엔티티에 설정합니다.
                .title(productDTO.getTitle())
                // 요청 DTO의 가격을 Product 엔티티에 설정합니다.
                .price(productDTO.getPrice())
                // 이미지 URL은 고정된 더미 이미지로 설정합니다.
                .imagesrc("https://dummyimage.com/200x200/00f/fff.jpg&text=product")
                // 빌더로 설정한 값을 바탕으로 Product 객체를 생성합니다.
                .build();

        // Product 객체를 데이터베이스에 저장하고, 저장된 객체를 반환받습니다.
        Product saveProduct = this.productRepository.save(product);

        // 저장된 Product를 다시 ProductDTO로 변환합니다. 클라이언트에 보낼 데이터입니다.
        ProductDTO saveProductDTO = ProductDTO.builder()
                // 저장된 상품의 ID를 DTO에 설정합니다.
                .id(saveProduct.getId())
                // 저장된 상품의 제목을 DTO에 설정합니다.
                .title(saveProduct.getTitle())
                // 저장된 상품의 가격을 DTO에 설정합니다.
                .price(saveProduct.getPrice())
                // 저장된 상품의 이미지 URL을 DTO에 설정합니다.
                .imagesrc(saveProduct.getImagesrc())
                // 빌더로 설정한 값을 바탕으로 ProductDTO 객체를 생성합니다.
                .build();

        // HTTP 상태 코드 201(Created)와 함께 저장된 상품 DTO를 응답으로 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(saveProductDTO);
    }

    @PutMapping(value = "/product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        // 요청 DTO의 ID를 사용해 데이터베이스에서 상품을 조회합니다. Optional로 반환됩니다.
        Optional<Product> productOptional = this.productRepository.findById(productDTO.getId());

        // 상품이 존재하는지 확인합니다. Optional의 isPresent() 메서드를 사용합니다.
        if (productOptional.isPresent()) {
            // Optional에서 Product 객체를 꺼냅니다.
            Product product = productOptional.get();
            // 요청 DTO의 가격으로 상품의 가격을 업데이트합니다.
            product.setPrice(productDTO.getPrice());

            // 수정된 Product 객체를 데이터베이스에 저장합니다.
            Product updateProduct = this.productRepository.save(product);

            // 수정된 Product를 ProductDTO로 변환합니다.
            ProductDTO updateProductDTO = ProductDTO.builder()
                    // 수정된 상품의 ID를 DTO에 설정합니다.
                    .id(updateProduct.getId())
                    // 수정된 상품의 제목을 DTO에 설정합니다.
                    .title(updateProduct.getTitle())
                    // 수정된 상품의 가격을 DTO에 설정합니다.
                    .price(updateProduct.getPrice())
                    // 수정된 상품의 이미지 URL을 DTO에 설정합니다.
                    .imagesrc(updateProduct.getImagesrc())
                    // 빌더로 설정한 값을 바탕으로 ProductDTO 객체를 생성합니다.
                    .build();

            // HTTP 상태 코드 200(OK)와 함께 수정된 상품 DTO를 응답으로 반환합니다.
            return ResponseEntity.status(HttpStatus.OK).body(updateProductDTO);
        }
        // 상품이 존재하지 않으면 HTTP 상태 코드 404(Not Found)를 반환합니다. 본문은 비어 있습니다.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        // 주어진 ID로 상품이 데이터베이스에 존재하는지 확인합니다.
        if (this.productRepository.existsById(id)) {
            // 존재하면 해당 ID의 상품을 데이터베이스에서 삭제합니다.
            this.productRepository.deleteById(id);
            // HTTP 상태 코드 200(OK)와 함께 성공 메시지를 응답으로 반환합니다.
            return ResponseEntity.status(HttpStatus.OK).body("상품 삭제 성공");
        }
        // 상품이 존재하지 않으면 HTTP 상태 코드 404(Not Found)와 함께 실패 메시지를 반환합니다.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제 상품 검색 실패");
    }
}