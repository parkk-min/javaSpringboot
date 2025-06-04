package com.example.authen_session.data.repository;

import com.example.authen_session.data.entity.AuthenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenRepository extends JpaRepository<AuthenEntity, String> {
}
