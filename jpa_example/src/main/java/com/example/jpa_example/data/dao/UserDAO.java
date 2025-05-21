package com.example.jpa_example.data.dao;

import com.example.jpa_example.data.entity.UserEntity;
import com.example.jpa_example.data.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDAO {
    private final UserEntityRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return this.userRepository.findAll();
    }

    public UserEntity getUserById(String id) {
        Optional<UserEntity> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

    public List<UserEntity> getUsersByAddr(String addr) {
        return userRepository.searchUserInfo(addr);
    }

    public List<UserEntity> getUserByBirthyear(Integer birthyear) {
        return userRepository.searchUserInfo(birthyear);
    }

    public List<UserEntity> getUserAddrBirthyear(String addr, Integer birthyear) {
        return userRepository.searchUserInfo(addr, birthyear);
    }


}
