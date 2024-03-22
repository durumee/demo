package com.hgr.demo.dto;

import com.hgr.demo.entity.MemberRent;
import lombok.Data;

import java.util.List;

@Data
public class MemberDTO {
    private Long memId;
    private String memPw;
    private String memNm;
    private String memCno;
    private List<MemberRent> listMemberRent;

}
