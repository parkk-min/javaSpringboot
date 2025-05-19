package com.example.my_test_backend.data.repository;

import com.example.my_test_backend.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}

//역할: JPA를 사용해 DB에 접근 (기본 CRUD 제공)
