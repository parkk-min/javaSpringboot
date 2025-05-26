package com.example.madang_project.service;

import com.example.madang_project.data.dao.OrderDAO;
import com.example.madang_project.data.dto.OrderDTO;
import com.example.madang_project.data.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public List<OrderDTO> getPreviousDate(LocalDate date) {
        List<OrderEntity> orderEntityList = this.orderDAO.previousOrderDate(date);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderId(orderEntity.getOrderId())
                    .custId(orderEntity.getCustId().getCustId())
                    .bookId(orderEntity.getBookId().getBookId())
                    .salePrice(orderEntity.getSalePrice())
                    .order_date(orderEntity.getOrder_date())
                    .build();
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    public List<OrderDTO> getAfterDate(LocalDate date) {
        List<OrderEntity> orderEntityList = this.orderDAO.AfterOrOrderDate(date);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderId(orderEntity.getOrderId())
                    .custId(orderEntity.getCustId().getCustId())
                    .bookId(orderEntity.getBookId().getBookId())
                    .salePrice(orderEntity.getSalePrice())
                    .order_date(orderEntity.getOrder_date())
                    .build();
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }


}
