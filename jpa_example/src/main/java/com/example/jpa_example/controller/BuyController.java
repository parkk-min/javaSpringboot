package com.example.jpa_example.controller;

import com.example.jpa_example.data.entity.BuyEntity;
import com.example.jpa_example.data.repository.BuyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuyController {
    private final BuyEntityRepository buyEntityRepository;

    @GetMapping(value = "buy-list")
    public List<BuyEntity> getBuyList() {
        return buyEntityRepository.findAll();
    }
}
