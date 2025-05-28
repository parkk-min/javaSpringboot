package com.example.authen_session.config;

import com.example.authen_session.component.CustomAuthenticationEntryPoint;
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
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        // 로그인 성공 시 호출되는 핸들러
        return (request, response, auth) -> {
            Map<String, Object> responseData = new HashMap<>(); // 응답할 데이터를 저장할 Map 생성
            responseData.put("result", "로그인 성공"); // 결과 메시지 삽입

            // 로그인한 사용자 정보 가져오기
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            responseData.put("username", userDetails.getUsername()); // 사용자 이름 저장
            responseData.put("role", userDetails.getAuthorities()); // 권한(ROLE) 정보 저장

            CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            responseData.put("csrf-token", token.getToken());

            // 객체를 JSON으로 변환하기 위한 Jackson ObjectMapper 사용
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(responseData); // Map -> JSON 문자열로 변환

            // 응답 설정
            response.setStatus(200); // HTTP 상태 코드 설정
            response.setContentType("application/json"); // 응답 타입 설정
            response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정
            response.getWriter().write(jsonMessage); // JSON 데이터 응답
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, auth) -> {
            Map<String, String> responseData = new HashMap<>(); // 응답할 데이터를 저장할 Map 생성
            responseData.put("result", "로그인 실패"); // 결과 메시지 삽입

            // 객체를 JSON으로 변환하기 위한 Jackson ObjectMapper 사용
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(responseData); // Map -> JSON 문자열 변환

            // 응답 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json"); // 응답 타입 설정
            response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정
            response.getWriter().write(jsonMessage); // JSON 데이터 응답
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, auth) -> {
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8"); // write에 한글이 들어가면 반드시 UTF-8이 필요하다. 영문만 있으면 제외가능
            response.getWriter().write("Logout success"); // write에 한글이 들어가면 반드시 UTF-8이 필요하다. 영문만 있으면 제외가능
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 보안 필터 체인 설정 시작
        // http.csrf(csrf -> csrf.disable()) // CSRF(사이트 간 위조 요청) 보호 비활성화

        // http 추가/ 활성화
        http.authorizeHttpRequests(authorize -> {
                    // URL 별 접근 권한 설정
                    authorize.requestMatchers("/", "/join", "/login", "/csrf-token").permitAll(); // 누구나 접근 가능
                    authorize.requestMatchers("/admin").hasRole("ADMIN");  // ADMIN 권한만 접근 가능
                    authorize.requestMatchers("/user").hasAnyRole("USER", "ADMIN"); // USER 또는 ADMIN 접근 가능
                    authorize.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN"); // /user 하위 경로도 위와 동일
                    authorize.anyRequest().authenticated();  // 그 외 모든 요청은 인증 필요
                })
                .formLogin(form ->
                        // 로그인 관련 설정
                        form.loginPage("/login") // 사용자 정의 로그인 페이지 경로
                                .successHandler(authenticationSuccessHandler())  // 성공 시 핸들러 설정
                                .failureHandler(authenticationFailureHandler()) // 실패 시 핸들러 설정
                )
                .logout(logout ->
                        // 클라이언트에서 "/logout" 주소로 요청이 오면 로그아웃 처리
                        logout.logoutUrl("/logout")
                                // 로그아웃 성공 시 실행할 핸들러 지정 (예: JSON으로 메시지 응답 등)
                                .logoutSuccessHandler(logoutSuccessHandler())
                                // 로그아웃 시 실행할 추가 작업 지정 (addLogoutHandler 사용)
                                .addLogoutHandler((request, response, authentication) -> {
                                    //사용자의 세션이 존재하면 세션을 무효화 (로그아웃 시 필수 작업)
                                    if (request.getSession() != null) {
                                        request.getSession().invalidate();
                                    }
                                    // Spring Security의 인증 정보도 초기화 (로그인 정보 제거)
                                    SecurityContextHolder.clearContext();
                                })
                                // 브라우저에서 저장된 JSESSIONID 쿠키도 삭제 (완전한 로그아웃)
                                .deleteCookies("JSESSIONID")
                )
                // CORS 설정: 다른 도메인에서 요청이 올 수 있도록 허용
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();  //  다른 포트/도메인의 프론트엔드에서 요청을 허용할지 설정하는 객체
                    corsConfiguration.addAllowedOrigin("http://localhost:3000"); // 허용할 프론트 주소
                    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000",
                            "http://localhost:3001", "http://localhost:3002")); // 여러 주소 허용
                    corsConfiguration.addAllowedHeader("*"); // 모든 헤더 허용
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드
                    corsConfiguration.setAllowCredentials(true); // 쿠키와 같은 인증 정보 전송 허용
                    return corsConfiguration; // 최종 설정 반환
                }))
                // 세션 관리 설정 종료
                .sessionManagement(session ->
                        session.maximumSessions(1) // 최대 허용 세션 수를 1로 제한. 즉, 한 사용자당 동시에 1개의 세션만 허용
                                .maxSessionsPreventsLogin(false) // 동일 사용자가 새로 로그인 시도 시 기존 세션을 유지하고 새 로그인을 허용 (true면 새 로그인 차단)
                                .expiredSessionStrategy(event -> {
                                    HttpServletResponse response = event.getResponse(); // HTTP 응답 객체를 가져옴
                                    response.setCharacterEncoding("UTF-8");
                                    response.getWriter().write("다른 호스트에서 로그인하여 현재 세션이 만료 되었습니다.");
                                }))
                .exceptionHandling(exception -> // 인증 예외 처리 설정 시작
                        exception.authenticationEntryPoint(this.customAuthenticationEntryPoint) // 인증 실패 시 호출할 커스텀 인증 진입점 설정
                ); // 예외 처리 설정 종료
        return http.build(); // 설정 완료 후 SecurityFilterChain 반환
    }

}
