package com.hgr.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "member_role")
@Data
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id", nullable = false)
    private Member member;

    @Column(name = "role_nm", nullable = false)
    private String roleNm;
}
