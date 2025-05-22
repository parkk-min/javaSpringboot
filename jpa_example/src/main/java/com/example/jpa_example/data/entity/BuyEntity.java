package com.example.jpa_example.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "buytbl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity userid;

    @Column(name = "prodname", nullable = false, length = 6)
    private String prodName;

    @Column(name = "groupname", length = 4)
    private String groupName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

}