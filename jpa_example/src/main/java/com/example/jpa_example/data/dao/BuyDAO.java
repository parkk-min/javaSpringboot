package com.example.jpa_example.data.dao;

import com.example.jpa_example.data.entity.BuyEntity;
import com.example.jpa_example.data.repository.BuyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyDAO {
    private final BuyEntityRepository buyRepository;

    public List<BuyEntity> getAllBuy() {
        return buyRepository.findAll();
    }

    public List<BuyEntity> getBuyById(String userid) {
        return this.buyRepository.searchUserInfo(userid);
    }

}
