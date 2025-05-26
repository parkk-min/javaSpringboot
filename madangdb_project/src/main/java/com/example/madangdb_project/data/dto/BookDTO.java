package com.example.madangdb_project.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDTO {
    private Integer  bookId;
    private String bookName;
    private String publisher;
    private Integer price;
    private List<OrderInfoDTO> orders;
}
