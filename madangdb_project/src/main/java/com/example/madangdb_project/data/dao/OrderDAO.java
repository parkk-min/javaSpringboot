package com.example.madangdb_project.data.dao;

import com.example.madangdb_project.data.entity.OrderEntity;
import com.example.madangdb_project.data.repository.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDAO {
    private final OrderEntityRepository orderEntityRepository;

    public List<OrderEntity> getOrderBefore(LocalDate date) {
        return orderEntityRepository.getOrderBefore(date);
    }

    public List<OrderEntity> getOrderAfter(LocalDate date) {

        return orderEntityRepository.getOrderAfter(date);
    }
}
