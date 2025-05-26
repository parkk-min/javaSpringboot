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
public class OrderDTO {
    private Integer orderId;
    private Integer custId;
    private Integer bookId;
    private Integer salePrice;
    private LocalDate orderDate;
}

