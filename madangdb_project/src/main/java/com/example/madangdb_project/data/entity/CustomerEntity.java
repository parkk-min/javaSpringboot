package com.example.madangdb_project.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custid", nullable = false)
    private Integer custid;

    @Size(max = 10)
    @NotNull
    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Size(max = 20)
    @NotNull
    @Column(name = "address", nullable = false, length = 20)
    private String address;

    @Size(max = 13)
    @Column(name = "phone", length = 13)
    private String phone;

    @OneToMany(mappedBy = "cust")
    private Set<OrderEntity> orders = new LinkedHashSet<>();

}