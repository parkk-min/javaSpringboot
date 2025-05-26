package com.example.madangdb_project.controller;

import com.example.madangdb_project.data.dto.OrderDTO;
import com.example.madangdb_project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/orderinfo")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "before")
    public ResponseEntity<List<OrderDTO>> before(@RequestParam LocalDate date) {
        List<OrderDTO> orderDTOList = this.orderService.getOrderBefore(date);
        if (orderDTOList.isEmpty()) {
            return ResponseEntity.status(250).build();
        }else{
            return ResponseEntity.ok(orderDTOList);
        }
    }

    @GetMapping(value = "after")
    public ResponseEntity<List<OrderDTO>> after(@RequestParam LocalDate date) {
        List<OrderDTO> orderDTOList = this.orderService.getOrderAfter(date);
        if (orderDTOList.isEmpty()) {
            return ResponseEntity.status(250).build();
        }else{
            return ResponseEntity.ok(orderDTOList);
        }
    }

}
