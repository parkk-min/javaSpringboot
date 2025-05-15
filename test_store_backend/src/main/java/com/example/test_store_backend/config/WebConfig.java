package com.example.test_store_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000") // 허용 할 출처. 다른 도메인의 요청은 차단
                .allowCredentials(true) // 쿠키 및 인증 정보 혀용(필요 시)
                .allowedHeaders("*") //모든 헤더 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); //허용 할 HTTP 메서드
    }
}

// CORS는 웹 브라우저가 다른 도메인에서 실행 중인 서버로 HTTP 요청을 보낼 때,
// 보안 정책으로 인해 요청이 차단되는 문제를 해결하기 위한 메커니즘입니다.
// 이 클래스는 프론트엔드(예: React 앱)와 백엔드 간의 원활한 통신을 가능하게 합니다.