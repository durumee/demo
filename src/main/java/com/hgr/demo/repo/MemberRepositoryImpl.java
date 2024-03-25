package com.hgr.demo.repo;

import com.hgr.demo.entity.Member;
import com.hgr.demo.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Member> findByCustomCondition(String condition) {
        QMember member = QMember.member;
        return queryFactory.selectFrom(member)
                .where(member.memLgnId.contains(condition))
                .fetch();
    }
    @Override
    public Optional<Member> findByDslOne(String condition) {
        QMember member = QMember.member;
        return Optional.ofNullable(queryFactory.selectFrom(member)
                .where(member.memLgnId.eq(condition))
                .fetchOne());
    }
}