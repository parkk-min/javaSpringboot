package com.example.madangdb_project.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderInfoDTO {
    private Integer orderId;
    private String custName;
    private String bookName;
    private Integer salePrice;
    private LocalDate orderDate;
}
