package com.example.madang_project.data.repository;

import com.example.madang_project.data.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "select * from orders where order_date<=:order_date", nativeQuery = true)
    List<OrderEntity> previousOrderDate(@Param("order_date") LocalDate date);

    @Query(value = "select * from orders where order_date>=:order_date", nativeQuery = true)
    List<OrderEntity> AfterOrOrderDate(@Param("order_date") LocalDate date);
}
