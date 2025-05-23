package com.example.jpa_example.data.repository;

import com.example.jpa_example.data.entity.BuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyEntityRepository extends JpaRepository<BuyEntity, String> {
    @Query(value = "select * from buytbl where userid=:userid", nativeQuery = true)
    List<BuyEntity> searchUserInfo(@Param("userid") String userid);

    @Query(value = "select  count(*) from buytbl where userid=:userid", nativeQuery = true)
    int searchUserInfoCount(@Param("userid") String userid);

}
