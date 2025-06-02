package com.example.authen_session.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component  // Spring Bean으로 등록해서 DI(의존성 주입)로 사용
public class JwtUtil {
    private SecretKey secretKey;

    // 생성자 주입 방식으로 application.properties (또는 yml)에서 jwt.secret.key 값을 주입받음
    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        // 시크릿 키 문자열을 HmacSHA256 알고리즘을 위한 SecretKey 객체로 변환
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    // JWT 토큰을 생성하는 메서드 (username, role, 만료시간을 받아서 토큰 생성)
    public String createToken(String username, String role, Long expiration) {
        return Jwts.builder()  // JWT 빌더 객체 생성
                .claim("username", username)  // 클레임(추가 정보)으로 username 추가
                .claim("role", role)          // 클레임(추가 정보)으로 role 추가
                .issuedAt(new Date(System.currentTimeMillis()))  // 토큰 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expiration))  // 만료 시간 설정
                .signWith(this.secretKey)    // 시크릿 키로 서명
                .compact();                  // 토큰 최종 생성
    }

    // 토큰에서 username을 꺼내는 메서드
    public String getUsername(String token) {
        return Jwts.parser()  // JWT 파서 생성
                .verifyWith(this.secretKey)  // 서명 검증을 위한 시크릿 키 설정
                .build()  // 파서 빌드
                .parseSignedClaims(token)  // 토큰 파싱
                .getPayload()              // Payload(클레임) 꺼내기
                .get("username").toString(); // username 클레임 추출
    }

    // 토큰에서 role을 꺼내는 메서드
    public String getRole(String token) {
        return Jwts.parser()  // JWT 파서 생성
                .verifyWith(this.secretKey)  // 서명 검증용 시크릿 키 설정
                .build()  // 파서 빌드
                .parseSignedClaims(token)  // 토큰 파싱
                .getPayload()              // Payload(클레임) 꺼내기
                .get("role").toString();   // role 클레임 추출
    }

    // 토큰이 만료되었는지 검사하는 메서드
    public Boolean isExpired(String token) {
        return Jwts.parser()  // JWT 파서 생성
                .verifyWith(this.secretKey)  // 서명 검증용 시크릿 키 설정
                .build()  // 파서 빌드
                .parseSignedClaims(token)  // 토큰 파싱
                .getPayload()              // Payload(클레임) 꺼내기
                .getExpiration()           // 만료 시간 꺼내기
                .before(new Date());       // 현재 시간과 비교해서 만료 여부 반환
    }
}
