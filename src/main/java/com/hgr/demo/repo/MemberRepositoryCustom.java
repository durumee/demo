package com.hgr.demo.repo;

import com.hgr.demo.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    List<Member> findByCustomCondition(String condition);
    Optional<Member> findByDslOne(String condition);
}