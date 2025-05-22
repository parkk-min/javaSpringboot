package com.example.jpa_example.controller;

import com.example.jpa_example.data.dto.UserDTO;
import com.example.jpa_example.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/userinfo")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "user-list")
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> userDTOList = this.userService.getAllUsers();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping(value = "addr/{addr}")
    public ResponseEntity<List<UserDTO>> getUsersByAddr(@PathVariable("addr") String addr) {
        List<UserDTO> userDTOList = this.userService.getUsersByAddr(addr);
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping(value = "birthyear/{birthyear}")
    public ResponseEntity<List<UserDTO>> getUserByBirthyear(@PathVariable("birthyear") Integer birthyear) {
        List<UserDTO> userDTOList = this.userService.getUserByBirthyear(birthyear);
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping(value = "addr-birthyear")
    public ResponseEntity<List<UserDTO>> getUserAddrBirthyear(
            @RequestParam("addr") String addr,
            @RequestParam("birthyear") Integer birthyear) {

        List<UserDTO> userDTOList = this.userService.getUserAddrBirthyear(addr, birthyear);
        return ResponseEntity.ok(userDTOList);
    }

    @PostMapping(value = "new-user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = this.userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);

    }


}
