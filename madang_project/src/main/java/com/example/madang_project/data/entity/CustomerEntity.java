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
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custid", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "customername", nullable = false, length = 10)
    private String customername;

    @Size(max = 15)
    @Column(name = "address", length = 15)
    private String address;

    @Size(max = 3)
    @Column(name = "phone1", length = 3)
    private String phone1;

    @Size(max = 8)
    @Column(name = "phone2", length = 8)
    private String phone2;

    @Size(max = 10)
    @ColumnDefault("'bronze'")
    @Column(name = "customer_class", length = 10)
    private String customerClass;

    @OneToMany(mappedBy = "custid")
//    @JsonManagedReference
    private Set<OrderEntity> orders = new LinkedHashSet<>();

}