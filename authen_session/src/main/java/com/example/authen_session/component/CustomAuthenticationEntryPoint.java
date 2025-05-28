package com.example.authen_session.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String,Object> responseData = new HashMap<>();
        // 응답 데이터를 담을 HashMap 생성
        // JSON 형식으로 에러 정보를 클라이언트(예: React)에 반환

        responseData.put("error","Unauthorized"); // 에러 유형: "Unauthorized" (인증 실패)
        responseData.put("message","먼저 로그인 하고 시도하세요."); // 사용자에게 보여줄 메시지

        ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 객체 생성: Java Map을 JSON 문자열로 변환
        String jsonMessage = objectMapper.writeValueAsString(responseData); // responseData를 JSON 문자열로 변환

        response.setContentType("application/json"); // HTTP 응답의 Content-Type을 JSON으로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setCharacterEncoding("UTF-8"); // write 한글 입력시 필수
        response.getWriter().write(jsonMessage);
    }

}
