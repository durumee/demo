package com.hgr.demo.service;

import com.hgr.demo.dto.MemberDTO;
import com.hgr.demo.entity.Member;
import com.hgr.demo.repo.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memLgnId) throws UsernameNotFoundException {
//        memberRepository.findByCustomCondition(memLgnId);
        Member member = memberRepository.findByDslOne(memLgnId).orElseThrow(() -> new UsernameNotFoundException("User not found with memLgnId: " + memLgnId));
        return new MemberDTO(member);
    }
}