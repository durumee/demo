package com.hgr.demo.repo;


import com.hgr.demo.entity.Member;
import com.hgr.demo.entity.QMember;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//    QMember qm = QMember.member;
//    JPAQuery<?> query = new JPAQuery<Void>(entityManager);
//    Member bob = query.select(qm)
//            .from(qm)
//            .where(qm.memNm.eq("user1"))
//            .fetchOne();
//
    Optional<Member> findByMemLgnId(String memLgnId);
}

//public class CustomMemberRepositoryImpl implements CustomMemberRepository {
//    private final JPAQueryFactory queryFactory;
//
//    public CustomMemberRepositoryImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public List<Member> findMembersByAge(int age) {
//        return queryFactory
//                .selectFrom(member)
//                .where(member.age.eq(age))
//                .fetch();
//    }
//}