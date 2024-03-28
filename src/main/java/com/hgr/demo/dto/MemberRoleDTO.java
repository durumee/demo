package com.hgr.demo.dto;

import com.hgr.demo.entity.Member;
import com.hgr.demo.entity.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MemberRoleDTO {
    private String memId;
    private String roleNm;

    public MemberRoleDTO(MemberRole member) {
        this.memId = member.getMember().getMemLgnId();
        this.roleNm = member.getRoleNm();
    }
}