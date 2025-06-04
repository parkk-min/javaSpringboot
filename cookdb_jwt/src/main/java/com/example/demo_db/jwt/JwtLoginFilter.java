package com.example.demo_db.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private Cookie createCookie(String key, String value) {

        // key는 쿠키 이름, value는 쿠키 값 (여기서는 refresh 토큰)
        Cookie cookie = new Cookie(key, value);

        // 쿠키의 경로를 애플리케이션 루트("/")로 설정
        // => 모든 경로에서 쿠키가 사용 가능하도록 함
        cookie.setPath("/reissue");

        // HttpOnly 옵션을 설정하여 자바스크립트에서 쿠키 접근 불가하게 함 (보안)
        cookie.setHttpOnly(true);

        // 쿠키 유효기간 설정 (60초 * 60분 * 24시간 = 1일)
        cookie.setMaxAge(60 * 60 * 24);

        // 완성된 쿠키 객체를 리턴
        return cookie;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request); // request에서 username을 추출 (스프링 시큐리티 제공 메서드)
        String password = obtainPassword(request); // request에서 password를 추출 (스프링 시큐리티 제공 메서드)

        // 인증용 객체 생성: UsernamePasswordAuthenticationToken(username, password)
        // credentials(비밀번호)와 authorities(권한)는 아직 null 상태
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, null);

        // 인증 처리: AuthenticationManager에게 토큰을 전달해 실제 인증 수행
        return this.authenticationManager.authenticate(authRequest);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {

        // 인증된 사용자 정보를 가져옴
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String username = userDetails.getUsername();

        // 로그인한 사용자의 권한 목록을 가져옴
        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
        // 권한 목록에서 첫 번째 권한 객체를 꺼냄
        Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
        // 실제 권한(Role)을 꺼냄
        GrantedAuthority grantedAuthority = iterator.next();
        // 권한(Role) 이름만 문자열로 꺼냄
        String role = grantedAuthority.getAuthority();

        // 클라이언트로 보낼 JSON 데이터 구성
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("role", role);
        responseData.put("result", "로그인성공");

        // Jackson 라이브러리를 이용해서 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(responseData);

        // JWT 토큰 생성
        String access_token = this.jwtUtil.createToken("access", username, role, 5 * 1000L);  // 5초 유효기간
        String refresh_token = this.jwtUtil.createToken("refresh", username, role, 60 * 60 * 24 * 1000L); // 24시간 유효기간

        // JWT 액세스 토큰을 Authorization 헤더에 추가 (Bearer 타입)
        response.addHeader("Authorization", "Bearer " + access_token);

        // JWT 리프레시 토큰은 보통 쿠키로 관리
        response.addCookie(this.createCookie("refresh", refresh_token));

        // 응답 문자셋 및 타입 설정
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonMessage);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", "로그인실패");
        responseData.put("result", failed.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(responseData);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonMessage);
    }
}
