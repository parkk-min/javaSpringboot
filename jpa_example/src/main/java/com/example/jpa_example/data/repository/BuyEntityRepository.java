package com.example.jpa_example.data.repository;

import com.example.jpa_example.data.entity.BuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyEntityRepository extends JpaRepository<BuyEntity, Integer> {
}
