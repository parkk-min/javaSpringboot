package com.example.madang_project.data.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Integer id;
    private Integer saleprice;
    private LocalDate orderdate;
    private Integer sellBookcount;

}
