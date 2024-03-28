package com.hgr.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "member_rent")
@Data
public class MemberRent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long memRentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;
}
