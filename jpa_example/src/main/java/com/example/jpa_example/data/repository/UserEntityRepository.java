package com.example.jpa_example.data.repository;

import com.example.jpa_example.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    @Query(value = "select * from usertbl where addr=:addr", nativeQuery = true)
    List<UserEntity> searchUserInfo(@Param("addr") String addr);
}
