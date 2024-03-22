package com.hgr.demo.dto;

import com.hgr.demo.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {
    private String memLgnId;
    private String memLgnPw;
    private String memNm;
    private String memCno;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Member member) {
        this.memLgnId = member.getMemLgnId();
        this.memLgnPw = member.getMemLgnPw();
        this.memNm = member.getMemNm();
        this.memCno = member.getMemCno();
        this.authorities = Collections.emptyList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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