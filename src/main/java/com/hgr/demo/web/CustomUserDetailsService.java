package com.hgr.demo.web;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 정보를 데이터베이스에서 조회하는 로직을 구현합니다.
        // 예시에서는 하드코딩된 사용자 정보를 사용합니다.
        if ("user".equals(username)) {
            return new CustomUserDetails("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        } else if ("admin".equals(username)) {
            return new CustomUserDetails("admin", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}