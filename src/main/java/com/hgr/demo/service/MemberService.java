package com.hgr.demo.service;

import com.hgr.demo.entity.Member;
import com.hgr.demo.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public Member getMemberByLgnId(String lgnId) {
        return memberRepository.findByMemLgnId(lgnId).orElseThrow();
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

}
