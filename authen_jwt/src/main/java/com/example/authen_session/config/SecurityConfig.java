package com.example.authen_session.config;

import com.example.authen_session.component.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 보안 필터 체인 설정 시작
        http.csrf(csrf -> csrf.disable()) // CSRF(사이트 간 위조 요청) 보호 비활성화
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                // http 추가/ 활성화
                .authorizeHttpRequests(authorize -> {
                    // URL 별 접근 권한 설정
                    authorize.requestMatchers("/", "/join", "/login").permitAll(); // 누구나 접근 가능
                    authorize.requestMatchers("/admin").hasRole("ADMIN");  // ADMIN 권한만 접근 가능
//                    authorize.requestMatchers("/user").hasAnyRole("USER", "ADMIN"); // USER 또는 ADMIN 접근 가능
//                    authorize.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN"); // /user 하위 경로도 위와 동일
                    authorize.anyRequest().authenticated();  // 그 외 모든 요청은 인증 필요
                })

                // CORS 설정: 다른 도메인에서 요청이 올 수 있도록 허용
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();  //  다른 포트/도메인의 프론트엔드에서 요청을 허용할지 설정하는 객체
                    corsConfiguration.addAllowedOrigin("http://localhost:3000"); // 허용할 프론트 주소
                    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001", "http://localhost:3002")); // 여러 주소 허용
                    corsConfiguration.addAllowedHeader("*"); // 모든 헤더 허용
                    corsConfiguration.setExposedHeaders(List.of("Authorization"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드
                    corsConfiguration.setAllowCredentials(true); // 쿠키와 같은 인증 정보 전송 허용
                    return corsConfiguration; // 최종 설정 반환
                }))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception -> // 인증 예외 처리 설정 시작
                        exception.authenticationEntryPoint(this.customAuthenticationEntryPoint) // 인증 실패 시 호출할 커스텀 인증 진입점 설정
                ); // 예외 처리 설정 종료
        return http.build(); // 설정 완료 후 SecurityFilterChain 반환
    }

}
