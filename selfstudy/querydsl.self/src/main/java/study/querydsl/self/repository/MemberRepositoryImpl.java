package study.querydsl.self.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.self.dto.MemberSearchCondition;
import study.querydsl.self.dto.MemberTeamDto;
import study.querydsl.self.dto.QMemberTeamDto;
import study.querydsl.self.entity.Member;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.self.entity.QMember.member;
import static study.querydsl.self.entity.QTeam.team;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public MemberRepositoryImpl(EntityManager em){
        super(Member.class);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

//    private final JPAQueryFactory queryFactory;
//
//    public MemberRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//
//    }

    public Page<MemberTeamDto> searchPageSimple2(MemberSearchCondition condition, Pageable pageable) {
        JPQLQuery<MemberTeamDto> jpaQuery = from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .select(new QMemberTeamDto(
                        member.id, member.username, member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")));


        JPQLQuery<MemberTeamDto> query = getQuerydsl().applyPagination(pageable, jpaQuery);
        List<MemberTeamDto> results = query.fetchResults().getResults();

        return new PageImpl<>(results, pageable, query.fetchCount());
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id, member.username, member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageBetween(condition.getAgeGoe(), condition.getAgeLoe())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> result = queryFactory
                .select(new QMemberTeamDto(
                        member.id, member.username, member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                );

        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> result = queryFactory
                .select(new QMemberTeamDto(
                        member.id, member.username, member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression ageBetween(int ageGoe, int ageLoe) {
        return ageGoe(ageGoe).and(ageLoe(ageLoe));
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return (ageGoe != null) ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return (ageLoe != null) ? member.age.loe(ageLoe) : null;
    }


    // =====================연습용 =========================

    @Override
    public Slice<MemberTeamDto> searchSliceDto(MemberSearchCondition condition) {
        List<MemberTeamDto> content = queryFactory
                .select(new QMemberTeamDto(
                        member.id, member.username, member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName"))
                )
                .from(member)
                .where()
                .orderBy(member.age.desc())
                .fetch();

        return new SliceImpl<>(content);
    }

    @Override
    public Slice<MemberTeamDto> searchSliceDtoPaging(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
                .select(new QMemberTeamDto(
                        member.id, member.username, member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName"))
                )
                .from(member)
                .where()
                .orderBy(member.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        boolean hasNext = content.size() == pageable.getPageSize() + 1;
        if(hasNext){
            content.remove(content.size() - 1);
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

}
