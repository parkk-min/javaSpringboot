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
public class CustomerDTO {
    private Integer custId;
    private String customerName;
    private String address;
    private String phone1;
    private String phone2;
    private String customer_class;
    private List<OrderInfoDTO> orders;
}
