package com.example.madang_project.controller;

import com.example.madang_project.data.dto.OrderDTO;
import com.example.madang_project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order-info")
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "previous")
    public ResponseEntity<List<OrderDTO>> getOrderPrevious(@RequestParam LocalDate date) {
        List<OrderDTO> combinedList = this.orderService.getPreviousDate(date);
        return ResponseEntity.ok(combinedList);
    }

    @GetMapping(value = "after")
    public ResponseEntity<List<OrderDTO>> getOrderAfter(@RequestParam LocalDate date) {
        List<OrderDTO> combinedList = this.orderService.getAfterDate(date);
        return ResponseEntity.ok(combinedList);
    }


}
