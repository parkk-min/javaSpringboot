package com.example.test_store_backend.controller;

import com.example.test_store_backend.data.Product;
import com.example.test_store_backend.data.ProductDTO;
import com.example.test_store_backend.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping(value = "/product-list")
    public List<Product> getProductList() {
        return this.productRepository.findAll();
    }

    @PostMapping(value = "/new-product") // "/new-product" 주소로 POST 요청이 오면 이 메서드가 실행돼요. 새 상품을 추가하는 기능!
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) { // 클라이언트(예: React)에서 보낸 데이터를 ProductDTO로 받아요. @RequestBody는 JSON 데이터를 객체로 바꿔줘요.
        Product product = new Product(); // 데이터베이스에 저장할 새 Product 객체를 만들어요. 이건 실제 DB에 들어갈 데이터예요.
        product.setPrice(productDTO.getPrice()); // 클라이언트가 보낸 가격을 Product 객체에 넣어요.
        product.setTitle(productDTO.getTitle()); // 클라이언트가 보낸 제목을 Product 객체에 넣어요.
        product.setImagesrc("https://dummyimage.com/200x200/00f/fff.jpg&text=product"); // 이미지 URL은 고정된 더미 이미지로 설정해요. (클라이언트가 이미지 안 보냈으니까)
        Product saveProduct = this.productRepository.save(product); // Product 객체를 데이터베이스에 저장하고, 저장된 객체를 받아와요.
        ProductDTO saveProductDTO = new ProductDTO(); // 클라이언트에게 보낼 새 ProductDTO 객체를 만들어요. 이건 응답용 편지 봉투!
        saveProductDTO.setPrice(saveProduct.getPrice()); // 저장된 상품의 가격을 DTO에 넣어요.
        saveProductDTO.setTitle(saveProduct.getTitle()); // 저장된 상품의 제목을 DTO에 넣어요.
        saveProductDTO.setImagesrc(saveProduct.getImagesrc()); // 저장된 상품의 이미지 URL을 DTO에 넣어요.
        saveProductDTO.setId(saveProduct.getId()); // 데이터베이스에서 만들어진 상품 ID를 DTO에 넣어요.
        return saveProductDTO; // 클라이언트에게 ProductDTO를 JSON으로 보내줘요. 이게 응답이에요!
    }

    @PutMapping(value = "/product")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        Optional<Product> productOp = this.productRepository.findById(productDTO.getId());
        if (productOp.isPresent()) {
            Product product = productOp.get();
            product.setPrice(productDTO.getPrice());
            Product updateProduct = this.productRepository.save(product);
            ProductDTO updateProductDTO = new ProductDTO();
            updateProductDTO.setId(updateProduct.getId());
            updateProductDTO.setPrice(updateProduct.getPrice());
            updateProductDTO.setTitle(updateProduct.getTitle());
            updateProductDTO.setImagesrc(updateProduct.getImagesrc());
            return updateProductDTO;
        }
        return null;
    }

    @DeleteMapping(value = "/product/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        if (this.productRepository.existsById(id)) {
            this.productRepository.deleteById(id);
            return "Product deleted successfully";
        }
        return "Product not found";
    }


}
