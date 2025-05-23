package com.example.madang_project.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "bookname")
    private String bookname;

    @Size(max = 255)
    @Column(name = "publisher")
    private String publisher;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @ColumnDefault("100")
    @Column(name = "stock_bookcount")
    private Integer stockBookcount;

    @OneToMany(mappedBy = "bookid")
//    @JsonManagedReference
    private Set<OrderEntity> orders = new LinkedHashSet<>();

}