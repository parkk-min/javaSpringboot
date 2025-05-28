package com.example.authen_test.config;

import com.example.authen_test.component.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean // Spring이 이 메서드의 반환 값을 Bean으로 등록해서 전체 프로젝트에서 재사용할 수 있게 해줍니다.
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder는 비밀번호를 해싱(hash)하는 클래스입니다.
        // 같은 문자열도 매번 다르게 암호화되며, 내부적으로 salt 값을 자동으로 추가합니다.
        // 이 방식은 보안에 강력하기 때문에 Spring Security에서 기본으로 권장됩니다.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        // 람다 형식으로 AuthenticationSuccessHandler 구현
        return (request, response, authentication) -> {
            // JSON 형태로 클라이언트에 응답할 데이터를 담을 Map 생성
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("result", "로그인 성공");

            // 인증된 사용자 정보를 가져옴 (Spring Security의 UserDetails 객체)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // 사용자 이름과 권한(역할)을 응답 데이터에 추가
            responseData.put("username", userDetails.getUsername());
            responseData.put("role", userDetails.getAuthorities());

            // ObjectMapper를 사용해 Map 데이터를 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(responseData);

            response.setStatus(200); // HTTP 상태 코드 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json); // 클라이언트로 JSON 데이터 전송
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            Map<String, String> responseData = new HashMap<>();
            responseData.put("result", "로그인 실패");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(responseData);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json);
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("logout");
        };
    }

    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {

        // 🔒 CSRF 보안 기능 비활성화 (REST API의 경우 일반적으로 disable함)
        http.csrf(csrf -> csrf.disable())

                // 🔑 URL 접근 권한 설정
                .authorizeHttpRequests(authorizeRequests -> {

                    // 누구나 접근 가능한 URL
                    authorizeRequests.requestMatchers("/", "/join", "/login").permitAll();

                    // /admin URL은 ADMIN 권한을 가진 사용자만 접근 가능
                    authorizeRequests.requestMatchers("/admin").hasRole("ADMIN");

                    // /user URL은 USER 또는 ADMIN 권한이 있어야 접근 가능
                    authorizeRequests.requestMatchers("/user").hasAnyRole("USER", "ADMIN");

                    // /user 하위 경로도 위와 동일
                    authorizeRequests.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN");

                    // 그 외 모든 요청은 인증된 사용자만 접근 가능
                    authorizeRequests.anyRequest().authenticated();
                })
                // 로그인 폼 설정
                .formLogin(formLogin ->
                        formLogin.loginPage("/login") // 사용자 정의 로그인 페이지
                                .successHandler(authenticationSuccessHandler()) // 로그인 성공 시 실행될 핸들러
                                .failureHandler(authenticationFailureHandler()) // 로그인 실패 시 실행될 핸들러
                )
                // 로그아웃 설정
                .logout(logout ->
                        logout.logoutUrl("/logout") // 로그아웃 URL
                                .logoutSuccessHandler(logoutSuccessHandler()) // 로그아웃 성공 시 핸들러
                                .addLogoutHandler((request, response, authentication) -> {
                                    // 세션이 존재할 경우 무효화
                                    if (request.getSession() != null) {
                                        request.getSession().invalidate();
                                    }
                                    // 시큐리티 컨텍스트 초기화
                                    SecurityContextHolder.clearContext();
                                })
                                .deleteCookies("JSESSIONID") // 쿠키 삭제
                )
                // CORS 설정 (프론트와 백엔드가 도메인이 다를 때 허용 처리)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("http://localhost:3000"); // 허용 도메인
                    corsConfiguration.addAllowedHeader("*");  // 모든 헤더 허용
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용 메소드
                    corsConfiguration.setAllowCredentials(true); // 인증정보 포함 허용 (예: 쿠키)
                    return corsConfiguration;
                }))
                .sessionManagement(session ->
                        session.maximumSessions(1) // 하나의 세션만 허용
                                .maxSessionsPreventsLogin(false) // 기존 세션을 끊고 새로 로그인 허용
                                .expiredSessionStrategy(event -> {
                                    // 세션 만료 시 메시지 전송
                                    HttpServletResponse response = event.getResponse();
                                    response.setCharacterEncoding("utf-8");
                                    response.getWriter().write("다른 호스트에서 로그인하여 현재 세션이 만료 되었습니다.");
                                })
                )
                // 인증 실패 시 처리 (예: 로그인 안하고 요청 시)
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(this.customAuthenticationEntryPoint)
                );
        // 모든 설정을 적용한 SecurityFilterChain 반환
        return http.build();
    }


}

//request	HttpServletRequest	클라이언트(브라우저)에서 보낸 요청 정보 전체
//response	HttpServletResponse	서버가 클라이언트에게 응답할 정보를 담는 객체
//authentication	Authentication	로그인에 성공한 사용자의 인증 정보
