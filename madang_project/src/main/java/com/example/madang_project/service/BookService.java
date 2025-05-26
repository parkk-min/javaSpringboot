package com.example.madang_project.service;

import com.example.madang_project.data.dao.BookDAO;
import com.example.madang_project.data.dto.BookDTO;
import com.example.madang_project.data.dto.OrderInfoDTO;
import com.example.madang_project.data.entity.BookEntity;
import com.example.madang_project.data.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDAO bookDAO;

    public BookDTO getBookById(Integer id) {
        BookEntity bookEntity = this.bookDAO.getBookById(id);
        List<OrderInfoDTO> orderInfoDTOList = new ArrayList<>();

        for (OrderEntity orderEntity : bookEntity.getOrders()) {
            OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder()
                    .orderId(orderEntity.getOrderId())
                    .bookName(bookEntity.getBookName())
                    .customerName(orderEntity.getCustId().getCustomerName())
                    .salePrice(orderEntity.getSalePrice())
                    .order_date(orderEntity.getOrder_date())
                    .build();
            orderInfoDTOList.add(orderInfoDTO);
        }
        BookDTO bookDTO = BookDTO.builder()
                .bookId(bookEntity.getBookId())
                .bookName(bookEntity.getBookName())
                .publisher(bookEntity.getPublisher())
                .price(bookEntity.getPrice())
                .orders(orderInfoDTOList)
                .build();
        return bookDTO;
    }


}
