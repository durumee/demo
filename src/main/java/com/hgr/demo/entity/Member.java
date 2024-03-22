package com.hgr.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "member")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memId;
    private String memLgnId;
    private String memLgnPw;
    private String memNm;
    private String memCno;
    @OneToMany(mappedBy = "member")
    private List<MemberRent> listMemberRent;

}
