package com.example.madang_project.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "custid", nullable = false)
//    @JsonBackReference
    private CustomerEntity custid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookid", nullable = false)
//    @JsonBackReference
    private BookEntity bookid;

    @NotNull
    @Column(name = "saleprice", nullable = false)
    private Integer saleprice;

    @NotNull
    @Column(name = "orderdate", nullable = false)
    private LocalDate orderdate;

    @ColumnDefault("1")
    @Column(name = "sell_bookcount")
    private Integer sellBookcount;

}