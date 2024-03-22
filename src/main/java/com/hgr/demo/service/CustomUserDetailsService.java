package com.hgr.demo.service;

import com.hgr.demo.dto.CustomUserDetails;
import com.hgr.demo.dto.MemberDTO;
import com.hgr.demo.entity.Member;
import com.hgr.demo.repo.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memLgnId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemLgnId(memLgnId).orElseThrow(() -> new UsernameNotFoundException("User not found with memLgnId: " + memLgnId));
        return new CustomUserDetails(member);
    }
}