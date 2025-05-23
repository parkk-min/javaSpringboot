package com.example.madang_project.data.dao;

import com.example.madang_project.data.entity.OrderEntity;
import com.example.madang_project.data.repository.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDAO {
    private final OrderEntityRepository orderEntityRepository;

    public List<OrderEntity> orderFindAll() {
        return orderEntityRepository.findAll();
    }

}
