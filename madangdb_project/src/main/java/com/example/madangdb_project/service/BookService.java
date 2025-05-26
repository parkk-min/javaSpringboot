package com.example.madangdb_project.service;

import com.example.madangdb_project.data.dao.BookDAO;
import com.example.madangdb_project.data.dto.BookDTO;
import com.example.madangdb_project.data.dto.OrderDTO;
import com.example.madangdb_project.data.dto.OrderInfoDTO;
import com.example.madangdb_project.data.entity.BookEntity;
import com.example.madangdb_project.data.entity.OrderEntity;
import com.example.madangdb_project.data.repository.BookEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDAO bookDAO;

    public BookDTO getBookInfo(Integer id) {
        BookEntity bookEntity = this.bookDAO.getBookInfo(id);
        if(bookEntity == null) {
            throw new EntityNotFoundException("존재하지 않는 책의 아이디입니다.");
        }
        List<OrderInfoDTO> orderInfoList = new ArrayList<>();

        for (OrderEntity orderEntity : bookEntity.getOrders()) {
            OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder()
                    .orderId(orderEntity.getOrderid())
                    .bookName(orderEntity.getBook().getBookname())
                    .custName(orderEntity.getCust().getName())
                    .salePrice(orderEntity.getSaleprice())
                    .orderDate(orderEntity.getOrderdate())
                    .build();
            orderInfoList.add(orderInfoDTO);
        }

        BookDTO bookDTO = BookDTO.builder()
                .bookId(bookEntity.getBookid())
                .bookName(bookEntity.getBookname())
                .publisher(bookEntity.getPublisher())
                .price(bookEntity.getPrice())
                .orders(orderInfoList).build();
        return bookDTO;
    }

}
