package com.example.madang_project.service;

import com.example.madang_project.data.dao.OrderDAO;
import com.example.madang_project.data.dto.OrderDTO;
import com.example.madang_project.data.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public List<OrderDTO> findAll() {
        List<OrderDTO> orderDTO = new ArrayList<>();
        List<OrderEntity> orderEntityList = this.orderDAO.orderFindAll();

    }
}
