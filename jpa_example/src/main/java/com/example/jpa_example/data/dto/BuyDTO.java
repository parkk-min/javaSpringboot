package com.example.jpa_example.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyDTO {
    private Integer id;
    private String userid;
    private String prodname;
    private String groupname;
    private Integer price;
    private Integer amount;
}
