package com.example.authen_session.service;

import com.example.authen_session.data.entity.AuthenEntity;
import com.example.authen_session.data.repository.AuthenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthenService implements UserDetailsService {
    private final AuthenRepository authenRepository;

    @Override
    // 로그인 시 Spring Security가 호출하는 메서드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  username으로 DB에서 사용자 찾기
        AuthenEntity authenEntity = this.authenRepository.findById(username).orElse(null);

        // 사용자가 없으면 예외 발생시킴 → Spring Security가 로그인 실패로 처리
        if (authenEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        // 사용자의 권한(Role)을 GrantedAuthority 형태로 변환
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(authenEntity.getRole())); // 예: "ROLE_USER", "ROLE_ADMIN" 등

        // UserDetails 객체 반환 (Spring Security가 내부에서 인증처리용으로 사용함)
        return new User(
                authenEntity.getUsername(), // 사용자 ID
                authenEntity.getPassword(), // 암호화된 비밀번호
                grantedAuthorities); // 권한 목록
    }
}
