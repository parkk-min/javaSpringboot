package com.example.madangdb_project.service;

import com.example.madangdb_project.data.dao.OrderDAO;
import com.example.madangdb_project.data.dto.OrderDTO;
import com.example.madangdb_project.data.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public List<OrderDTO> getOrderBefore(LocalDate date) {
        List<OrderEntity> orderEntities = orderDAO.getOrderBefore(date);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderId(orderEntity.getOrderid())
                    .bookId(orderEntity.getBook().getBookid())
                    .custId(orderEntity.getCust().getCustid())
                    .salePrice(orderEntity.getSaleprice())
                    .orderDate(orderEntity.getOrderdate())
                    .build();
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    public List<OrderDTO> getOrderAfter(LocalDate date) {
        List<OrderEntity> orderEntities = orderDAO.getOrderAfter(date);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderId(orderEntity.getOrderid())
                    .bookId(orderEntity.getBook().getBookid())
                    .custId(orderEntity.getCust().getCustid())
                    .salePrice(orderEntity.getSaleprice())
                    .orderDate(orderEntity.getOrderdate())
                    .build();
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

}
