package com.example.jpa_example.data.dao;

import com.example.jpa_example.data.entity.BuyEntity;
import com.example.jpa_example.data.entity.UserEntity;
import com.example.jpa_example.data.repository.BuyEntityRepository;
import com.example.jpa_example.data.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyDAO {
    private final BuyEntityRepository buyRepository;
    private final UserEntityRepository userRepository;

    public List<BuyEntity> getAllBuy() {
        return buyRepository.findAll();
    }

    public List<BuyEntity> getBuyById(String userid) {
        return this.buyRepository.searchUserInfo(userid);
    }

    public BuyEntity saveBuyList(UserEntity user, String prodname, String groupname, Integer price, Integer amount) {
        BuyEntity buy = BuyEntity.builder()
                .userid(user)
                .prodName(prodname)
                .groupName(groupname)
                .price(price)
                .amount(amount)
                .build();
        return this.buyRepository.save(buy);
    }

}
