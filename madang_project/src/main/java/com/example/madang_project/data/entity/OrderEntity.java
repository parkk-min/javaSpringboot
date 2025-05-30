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
    private Integer orderId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "custid", nullable = false)
//    @JsonBackReference
    private CustomerEntity custId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookid", nullable = false)
//    @JsonBackReference
    private BookEntity bookId;

    @NotNull
    @Column(name = "saleprice", nullable = false)
    private Integer salePrice;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate order_date;

    @ColumnDefault("1")
    @Column(name = "sell_bookcount")
    private Integer sell_bookcount;
}