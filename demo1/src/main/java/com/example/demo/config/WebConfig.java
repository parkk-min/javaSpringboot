package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

// CORS 설정을 위해 필요한 도구를 가져오는 코드
// CORS는 다른 주소에서 온 요청을 허용할지 결정하는 브라우저의 보안 규칙
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 이 클래스를 스프링 설정 파일로 등록한다는 의미
@Configuration
// WebMvcConfigurer 라는 기능을 사용해서 웹 관련 설정을 바꿀 수 있다
public class WebConfig implements WebMvcConfigurer {

    // CORS 설정
    // CORS는 다른 주소(예: 프론트엔드와 백엔드가 다른 포트나 도메인일 때)에서도
    // 내 서버에 요청할 수 있게 허락해주는 규칙
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로(/**)에 대해서 CORS 허용
        registry.addMapping("/**")
                // 이 주소에서 오는 요청만 허용
                // 여기서는 React 개발 서버 주소인 http://localhost:3000을 허용
                .allowedOrigins("http://localhost:3000")
                // 쿠키나 로그인 정보도 같이 보낼 수 있게 허용
                .allowCredentials(true)
                // 어떤 HTTP 요청들을 허용할지 정하는 부분
                // GET(조회), POST(생성), PUT(수정), DELETE(삭제), OPTIONS(확인용 요청)을 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 어떤 요청 헤더들을 허용할지 정하는 부분
                // "*"는 모든 헤더를 허용하겠다는 뜻
                .allowedHeaders("*");

        // 이 설정을 해주면 React(프론트엔드)에서 내 스프링 서버로 API 요청을 할 때,
        // "CORS 오류"가 나지 않고 정상적으로 요청이 가능해짐
    }
}


