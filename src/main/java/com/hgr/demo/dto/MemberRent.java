package com.hgr.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "member_rent")
@Data
public class MemberRent {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int memRentId;

    @ManyToOne
    @JoinColumn(name = "mem_id")
    private Member member;


}
