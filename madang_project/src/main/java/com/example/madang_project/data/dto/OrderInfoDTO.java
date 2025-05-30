package com.example.madang_project.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoDTO {
    private Integer orderId;
    private String customerName;
    private String bookName;
    private Integer salePrice;
    private LocalDate order_date;
}
