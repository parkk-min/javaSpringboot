package com.example.jpa_example.controller;

import com.example.jpa_example.data.dto.BuyDTO;
import com.example.jpa_example.service.BuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuyController {
    private final BuyService buyService;

    @GetMapping(value = "buy-list")
    public ResponseEntity<List<BuyDTO>> getBuyList() {
        List<BuyDTO> buyDTOList = this.buyService.getAllBuys();
        return ResponseEntity.ok(buyDTOList);
    }

    @GetMapping(value = "buy-list/{userid}")
    public ResponseEntity<List<BuyDTO>> getBuyListByUserid(@PathVariable("userid") String userid) {
        List<BuyDTO> buyDTOList = this.buyService.getBuyById(userid);
        if(buyDTOList.isEmpty()){
            return ResponseEntity.status(250).build();
        }
        return ResponseEntity.ok(buyDTOList);
    }
    @PostMapping(value = "new-list")
    public ResponseEntity<BuyDTO> createBuy(@RequestBody BuyDTO buyDTO) {
        BuyDTO savedBuyDTO = this.buyService.saveBuy(buyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBuyDTO);
    }
}
