package com.hgr.demo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

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
    @OneToMany (mappedBy = "member")
    private List<MemberRent> listMemberRent;

}
