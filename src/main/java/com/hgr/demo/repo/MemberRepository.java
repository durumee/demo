package com.hgr.demo.repo;


import com.hgr.demo.entity.Member;
import com.hgr.demo.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByMemLgnId(String memLgnId);

    @Query("SELECT m FROM MemberRole m WHERE m.member.memId = :memId")
    List<MemberRole> findRolesByMemId(@Param("memId") Long memId);
}
