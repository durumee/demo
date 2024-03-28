package com.hgr.demo.repo;


import com.hgr.demo.entity.Member;
import com.hgr.demo.entity.MemberRent;
import com.hgr.demo.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRentRepository extends JpaRepository<MemberRent, Long> {
}
