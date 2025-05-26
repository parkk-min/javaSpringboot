package com.example.madangdb_project.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid", nullable = false)
    private Integer bookid;

    @Size(max = 255)
    @Column(name = "bookname")
    private String bookname;

    @Size(max = 255)
    @Column(name = "publisher")
    private String publisher;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private Set<OrderEntity> orders = new LinkedHashSet<>();

}