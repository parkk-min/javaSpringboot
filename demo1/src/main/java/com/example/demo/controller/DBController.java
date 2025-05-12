package com.example.demo.controller;

// 필요한 자바/스프링 클래스 가져오기

import com.example.demo.data.User; // 사용자 데이터를 담는 클래스
import com.example.demo.data.UserRepository; // DB와 통신하는 도구
import lombok.RequiredArgsConstructor; // Lombok으로 생성자를 자동 생성
import org.springframework.web.bind.annotation.*;

import java.util.List; // 여러 데이터를 담는 리스트
import java.util.Optional; // 데이터가 있을 수도, 없을 수도 있는 상자

// 이 클래스는 웹 API를 처리 (JSON 데이터를 돌려줌)
@RestController
// Lombok: 필요한 생성자를 자동으로 만들어줌
@RequiredArgsConstructor
public class DBController {
    // DB와 통신하는 도구 (스프링이 자동으로 주입)
    private final UserRepository userRepository;
    // 주석 처리된 생성자: @RequiredArgsConstructor가 이걸 대신 만들어줌
//    public DBController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    // "/userlist" URL로 GET 요청이 오면 모든 사용자 목록을 반환
    @GetMapping(value = "/userlist")
    public List<User> getUserList() {
        // DB에서 모든 사용자 데이터를 가져옴
        List<User> userlist = this.userRepository.findAll();
        // 가져온 데이터를 JSON으로 돌려줌
        return userlist;
    }

    // "/userlist/{id}" URL로 GET 요청이 오면 특정 사용자 데이터를 반환
    @GetMapping(value = "/userlist/{id}")
    public User getUser(@PathVariable String id) {
        // DB에서 ID로 사용자 데이터를 찾음 (데이터가 있을 수도, 없을 수도 있음)
        Optional<User> user = this.userRepository.findById(id);
        // 데이터가 있으면 User 객체를 꺼내서 반환
        if (user.isPresent()) {
            return user.get();
        }
        // 데이터가 없으면 null 반환
        return null;
    }

    @PostMapping(value = "/user")
    public User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @DeleteMapping(value = "/user/{var}")
    public String deleteUser(@PathVariable String var) {
        if (this.userRepository.existsById(var)) {
            this.userRepository.deleteById(var);
            return "Delete success";
        }
        return "Delete Fail";
    }
}