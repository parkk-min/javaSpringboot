package com.example.my_test_backend.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "producttbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer price;
    private String imagesrc;
    private LocalDateTime created; //생성 시간을 저장하는 필드
    private String description; //설명, 부가적인 정보를 담는 텍스트 필드
}

//역할: DB 테이블과 1:1로 매핑되는 클래스
//@Entity, @Table, @Id, @Column 등을 사용하여 정의
