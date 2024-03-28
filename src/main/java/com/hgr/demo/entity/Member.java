package com.hgr.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@ToString(exclude = {"listMemberRent", "listMemberRole"})
public class Member {
    public Member(Long memId) {
        this.memId = memId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memId;
    private String memLgnId;
    private String memLgnPw;
    private String memNm;
    private String memCno;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRent> listMemberRent;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRole> listMemberRole;
}
