package com.example.madang_project.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private Integer bookId;
    private String bookName;
    private String publisher;
    private String price;
    private List<OrderInfoDTO> orders;
}
