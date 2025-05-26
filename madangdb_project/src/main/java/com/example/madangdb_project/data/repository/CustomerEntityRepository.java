package com.example.madangdb_project.data.repository;

import com.example.madangdb_project.data.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Integer> {
}
