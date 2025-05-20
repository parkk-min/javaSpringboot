package com.example.jpa_example.controller;

import com.example.jpa_example.data.entity.BuyEntity;
import com.example.jpa_example.data.entity.UserEntity;
import com.example.jpa_example.data.repository.BuyEntityRepository;
import com.example.jpa_example.data.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserEntityRepository userRepository;
    private final BuyEntityRepository buyEntityRepository;

    @GetMapping(value = "/user-list")
    public List<UserEntity> userList() {
        return this.userRepository.findAll();
    }

    @GetMapping(value = "/buy-list")
    public List<BuyEntity> buyList() {
        return this.buyEntityRepository.findAll();
    }

    @GetMapping(value = "/userInfo/{addr}")
    public List<UserEntity> userInfo(@PathVariable("addr") String addr) {
        return this.userRepository.searchUserInfo(addr);
    }


}
