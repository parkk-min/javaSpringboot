package com.example.demo_db.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 Authorization 값 꺼내오기
        String token = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면 그냥 다음 필터로 넘김
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // "Bearer " 부분 잘라내고 실제 토큰만 꺼내오기
        token = token.split(" ")[1];

        try {
            this.jwtUtil.isExpired(token);  // 토큰이 만료되었는지 확인
        } catch (ExpiredJwtException e) {
            response.getWriter().write("access token expired");  // 토큰 만료 시 사용자에게 메시지 반환
            response.setStatus(456);  // 상태코드 456(사용자 정의)로 만료 표시
            response.setCharacterEncoding("UTF-8");
            return;
        }

        // 토큰에서 category(토큰 타입) 가져오기
        String category = this.jwtUtil.getCategory(token);
        // access 카테고리만 통과 가능
        if (!category.equals("access")) {
            response.getWriter().write("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태코드 401(UNAUTHORIZED)로 유효하지 않음을 표시
            response.setCharacterEncoding("UTF-8");
            return;
        }

        // 유효한 토큰이면 사용자 정보(username, role) 꺼내오기
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // 권한(Role) 정보를 GrantedAuthority로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        // 스프링 시큐리티가 요구하는 UserDetails 객체 생성 (패스워드는 필요 없으므로 빈 문자열)
        User user = new User(username, "", authorities);

        // 인증 객체 생성 (UserDetails, Credentials(비번 없음), 권한 리스트)
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);

        // SecurityContextHolder에 인증 객체 등록 => 이후 컨트롤러에서 @AuthenticationPrincipal로 사용자 정보 주입 가능
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);
    }
}
