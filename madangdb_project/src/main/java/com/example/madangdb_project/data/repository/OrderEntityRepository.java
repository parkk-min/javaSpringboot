package com.example.madangdb_project.data.repository;

import com.example.madangdb_project.data.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {
    @Query(value = "select * from orders where orderdate <= :date", nativeQuery = true)
    List<OrderEntity> getOrderBefore(@Param("date") LocalDate date);

    @Query(value = "select * from orders where orderdate >= :date", nativeQuery = true)
    List<OrderEntity> getOrderAfter(@Param("date") LocalDate date);

}
