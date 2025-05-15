package com.example.my_test_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String title;
    private Integer price;
    private String imagesrc;
}
