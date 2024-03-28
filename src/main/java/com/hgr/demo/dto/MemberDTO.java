package com.hgr.demo.dto;

import com.hgr.demo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO implements UserDetails {
    private String memLgnId;
    private String memLgnPw;
    private String memNm;
    private String memCno;

    private List<String> roles = new ArrayList<>();

    private Collection<? extends GrantedAuthority> authorities;

//    public MemberDTO(List<MemberRole> memRoles) {
//        memRoles.forEach(memRole -> {
//            this.memLgnId = memRole.getMember().getMemLgnId();
//            this.memLgnPw = memRole.getMember().getMemLgnPw();
//            this.memNm = memRole.getMember().getMemNm();
//            this.memCno = memRole.getMember().getMemCno();
//            this.roles.add(memRole.getRoleNm());
//            this.authorities = Collections.emptyList();
//        });
//    }
    public MemberDTO(Member member) {
        this.memLgnId = member.getMemLgnId();
        this.memLgnPw = member.getMemLgnPw();
        this.memNm = member.getMemNm();
        this.memCno = member.getMemCno();
    }
    public MemberDTO(Member member, List<GrantedAuthority> authorities) {
        this.memLgnId = member.getMemLgnId();
        this.memLgnPw = member.getMemLgnPw();
        this.memNm = member.getMemNm();
        this.memCno = member.getMemCno();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return memLgnPw;
    }

    @Override
    public String getUsername() {
        return memLgnId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}