package com.example.demo.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "usertbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userid;
    private String username;
    private int birthyear;
    private String addr;
    private String mobile1;
    private String mobile2;
    private int height;
    private Date mdate;
}
