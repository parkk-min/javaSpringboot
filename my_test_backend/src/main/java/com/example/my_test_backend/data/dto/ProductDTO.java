package com.example.my_test_backend.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    @NotEmpty(message = "이름을 비어 있을 수 없습니다.")
    private String title;
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;
    private String imagesrc;
}

//역할: 클라이언트와 데이터 교환에 사용하는 객체 (Entity와 분리)
//Entity보다 가볍고, 외부 노출 시 민감 정보 숨김 처리 가능
