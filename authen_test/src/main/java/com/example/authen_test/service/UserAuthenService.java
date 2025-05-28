package com.example.authen_test.service;

import com.example.authen_test.data.entity.AuthenEntity;
import com.example.authen_test.data.repository.AuthenRepository;
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
    private final AuthenRepository authenRepository; // DB에서 사용자 정보 조회할 리포지토리 주입


    // 로그인할 때 사용자 정보를 DB에서 찾아주는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자가 로그인할 때 입력한 username을 기반으로 DB에서 사용자 정보를 가져옴
        AuthenEntity authenEntity = this.authenRepository.findByUsername(username);

        // 사용자가 존재하지 않으면 예외 발생 → Spring Security가 로그인 실패 처리
        if (authenEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        // 권한(ROLE)을 저장할 리스트 생성
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 예: ROLE_USER 또는 ROLE_ADMIN 같은 권한을 부여
        grantedAuthorities.add(new SimpleGrantedAuthority(authenEntity.getRole()));

        // Spring Security가 내부적으로 사용하는 User 객체 생성
        return new User(
                authenEntity.getUsername(),
                authenEntity.getPassword(),
                grantedAuthorities);
    }
}
