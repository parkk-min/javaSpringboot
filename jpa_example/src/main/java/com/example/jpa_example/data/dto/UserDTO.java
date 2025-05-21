package com.example.jpa_example.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String name;
    private Integer birthyear;
    private String addr;
    private String mobile1;
    private String mobile2;
    private Integer height;
    private Instant mdate;
}
