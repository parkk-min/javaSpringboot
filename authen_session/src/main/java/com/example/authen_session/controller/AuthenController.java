package com.example.authen_session.controller;

import com.example.authen_session.data.dto.AuthenDTO;
import com.example.authen_session.data.entity.AuthenEntity;
import com.example.authen_session.data.repository.AuthenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenController {
    private final AuthenRepository authenRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/")
    public String index() {
        return "Hello World";
    }


    @GetMapping(value = "/admin")
    public String admin() {
        return "Hello Admin";
    }


    @GetMapping(value = "/csrf-token")
    public ResponseEntity<Map<String, String>> csrfToken(HttpServletRequest request) {

        // HttpServletRequest 에서 Spring이 자동으로 생성해준 CsrfToken 객체를 가져옴
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // 반환할 데이터를 담을 Map 생성
        Map<String, String> map = new HashMap<>();

        // 키 "csrf-token"에 실제 CSRF 토큰 값을 넣음
        map.put("csrf-token", csrfToken.getToken());

        // 클라이언트에게 200 OK와 함께 Map을 JSON 형태로 반환
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody AuthenDTO authenDTO) {
        if (this.authenRepository.existsById(authenDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
        AuthenEntity authenEntity = AuthenEntity.builder()
                .username(authenDTO.getUsername())
                .password(this.passwordEncoder.encode(authenDTO.getPassword()))
                .role("ROLE_USER")
                .build();
        this.authenRepository.save(authenEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("가입성공!");
    }


}
