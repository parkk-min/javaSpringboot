package com.example.authen_test.data.repository;

import com.example.authen_test.data.entity.AuthenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenRepository extends JpaRepository<AuthenEntity, String> {
    AuthenEntity findByUsername(String username);
}
