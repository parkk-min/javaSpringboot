package com.example.jpa_example.service;

import com.example.jpa_example.data.dao.BuyDAO;
import com.example.jpa_example.data.dto.BuyDTO;
import com.example.jpa_example.data.entity.BuyEntity;
import com.example.jpa_example.data.entity.UserEntity;
import com.example.jpa_example.data.repository.UserEntityRepository;
import com.example.jpa_example.exception.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyService {
    private final BuyDAO buyDAO;
    private final UserEntityRepository userEntityRepository;

    public List<BuyDTO> getAllBuys() {
        List<BuyDTO> buyDTOList = new ArrayList<>();
        List<BuyEntity> buyList = this.buyDAO.getAllBuy();
        for (BuyEntity buy : buyList) {
            BuyDTO buyDTO = BuyDTO.builder()
                    .id(buy.getId())
                    .userid(buy.getUserid().getUserID())
                    .prodname(buy.getProdName())
                    .groupname(buy.getGroupName())
                    .price(buy.getPrice())
                    .amount(buy.getPrice())
                    .build();
            buyDTOList.add(buyDTO);
        }
        return buyDTOList;
    }

    public List<BuyDTO> getBuyById(String userId) {
        List<BuyEntity> buyList = this.buyDAO.getBuyById(userId);
        List<BuyDTO> buyDTOList = new ArrayList<>();
        if (buyList == null) {
            throw new MyException("현재 구매내역이 없습니다.");
        }
        for (BuyEntity buy : buyList) {
            BuyDTO dto = BuyDTO.builder()
                    .id(buy.getId())
                    .userid(buy.getUserid().getUserID())
                    .prodname(buy.getProdName())
                    .groupname(buy.getGroupName())
                    .price(buy.getPrice())
                    .amount(buy.getAmount())
                    .build();
            buyDTOList.add(dto);
        }
        return buyDTOList;
    }

    public BuyDTO saveBuy(BuyDTO buyDTO) {
        UserEntity user = userEntityRepository.findById(buyDTO.getUserid())
                .orElseThrow(() -> new MyException("존재하지 않는 사용자입니다."));

        BuyEntity buy = this.buyDAO.saveBuyList(user, buyDTO.getProdname(),
                buyDTO.getGroupname(), buyDTO.getPrice(), buyDTO.getAmount());
        BuyDTO saveBuyDTO = BuyDTO.builder()
                .userid(buy.getUserid().getUserID())
                .prodname(buy.getProdName())
                .groupname(buy.getGroupName())
                .price(buy.getPrice())
                .amount(buy.getAmount())
                .build();
        return saveBuyDTO;
    }

}
