package com.example.test_store_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// open 된 정보만 담아놓는 저장소 = DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String title;
    private String imagesrc;
    private Integer price;
}
