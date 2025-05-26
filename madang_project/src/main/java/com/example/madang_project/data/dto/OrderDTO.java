package com.example.madang_project.data.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Integer orderId;
    private Integer bookId;
    private Integer custId;
    private Integer salePrice;
    private LocalDate order_date;
    private Integer sell_bookcount;
}
