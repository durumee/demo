package com.hgr.demo.service;

import com.hgr.demo.dto.MemberDTO;
import com.hgr.demo.entity.Member;
import com.hgr.demo.entity.MemberRent;
import com.hgr.demo.entity.MemberRole;
import com.hgr.demo.repo.MemberRentRepository;
import com.hgr.demo.repo.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberRentRepository mrRepo;

    public MemberService(MemberRepository memberRepository, MemberRentRepository mrRepo) {
        this.memberRepository = memberRepository;
        this.mrRepo = mrRepo;
    }

    public Page<MemberRent> getRents(int pageNo) throws Exception {
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("memRentId").descending());
        return mrRepo.findAll(pageable);
    }

    public void addRent() throws Exception {
        List<MemberRent> mrList = new ArrayList<>();
        MemberRent mrObj = new MemberRent();
        Member member = new Member(1L);

        for (int i = 1; i <= 50; ++i) {
            mrObj = new MemberRent();
            mrObj.setMemRentId(Integer.toUnsignedLong(i));
            mrObj.setMember(member);
            mrList.add(mrObj);
        }

        mrRepo.saveAll(mrList);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String memLgnId) {
        log.info(":: authenticate :: {}", memLgnId);
        //로그인ID로 사용자 정보 조회
        Member member = memberRepository.findByDslOne(memLgnId).orElseThrow(() -> new UsernameNotFoundException("User not found with memLgnId: " + memLgnId));
        //로그인ID로 권한 정보 조회
        List<MemberRole> memRoles = memberRepository.findRolesByMemId(member.getMemId());
        log.info("member :: {}", member.getMemLgnPw());

        //권한 정보를 스프링 시큐리티 권한 문자열로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();
        memRoles.forEach(memRole -> {
                    authorities.add(new SimpleGrantedAuthority(("ROLE_" + memRole.getRoleNm())));
                }
        );

        //100. (DB) 회원권한 테이블 생성
        //200. (UserDetailsService 또는 그 구현 클래스) 로그인 시 로그인ID의 권한 정보 조회
        //300. (UserDetailsService 에서 관리하는 UserDetails 또는 그 구현 클래스) UserDetails의 authorities 객체에 해당 권한 정보를 세팅

        return new MemberDTO(member, authorities);
    }
}