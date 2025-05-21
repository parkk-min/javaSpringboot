package com.example.jpa_example.service;

import com.example.jpa_example.data.dao.UserDAO;
import com.example.jpa_example.data.dto.UserDTO;
import com.example.jpa_example.data.entity.UserEntity;
import com.example.jpa_example.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<UserEntity> userList = this.userDAO.getAllUsers();
        for (UserEntity user : userList) {
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getUserID())
                    .name(user.getUsername())
                    .birthyear(user.getBirthYear())
                    .addr(user.getAddr())
                    .mobile1(user.getMobile1())
                    .mobile2(user.getMobile2())
                    .height(user.getHeight())
                    .mdate(user.getMdate())
                    .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO getUserById(String userId) {
        UserEntity userEntity = this.userDAO.getUserById(userId);
        if (userEntity == null) {
            throw new UserException("현재 User의 정보가 없습니다.");
        }
        UserDTO userDTO = UserDTO.builder()
                .id(userEntity.getUserID())
                .name(userEntity.getUsername())
                .birthyear(userEntity.getBirthYear())
                .addr(userEntity.getAddr())
                .mobile1(userEntity.getMobile1())
                .mobile2(userEntity.getMobile2())
                .height(userEntity.getHeight())
                .mdate(userEntity.getMdate())
                .build();
        return userDTO;
    }

    public List<UserDTO> getUsersByAddr(String addr) {
        List<UserEntity> addrList = this.userDAO.getUsersByAddr(addr);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : addrList) {
            userDTOList.add(UserDTO.builder()
                    .id(user.getUserID())
                    .name(user.getUsername())
                    .birthyear(user.getBirthYear())
                    .addr(user.getAddr())
                    .mobile1(user.getMobile1())
                    .mobile2(user.getMobile2())
                    .height(user.getHeight())
                    .mdate(user.getMdate())
                    .build());
        }
        return userDTOList;
    }

    public List<UserDTO> getUserByBirthyear(Integer birthyear) {
        List<UserEntity> birthyearList = this.userDAO.getUserByBirthyear(birthyear);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : birthyearList) {
            userDTOList.add(UserDTO.builder()
                    .id(user.getUserID())
                    .name(user.getUsername())
                    .birthyear(user.getBirthYear())
                    .addr(user.getAddr())
                    .mobile1(user.getMobile1())
                    .mobile2(user.getMobile2())
                    .height(user.getHeight())
                    .mdate(user.getMdate())
                    .build());
        }
        return userDTOList;
    }

    public List<UserDTO> getUserAddrBirthyear(String addr, Integer birthyear) {
        List<UserEntity> addrBirthyearList = this.userDAO.getUserAddrBirthyear(addr, birthyear);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : addrBirthyearList) {
            userDTOList.add(UserDTO.builder()
                    .id(user.getUserID())
                    .name(user.getUsername())
                    .birthyear(user.getBirthYear())
                    .addr(user.getAddr())
                    .mobile1(user.getMobile1())
                    .mobile2(user.getMobile2())
                    .height(user.getHeight())
                    .mdate(user.getMdate())
                    .build());
        }
        return userDTOList;
    }


}
