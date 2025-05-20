package com.example.test_store_backend.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// open 된 정보만 담아놓는 저장소 = DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    @NotEmpty(message = "이름은 비어 있을 수 없습니다.")
    private String title;
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;
    private String imagesrc;

}
