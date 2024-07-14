package study.querydsl.start.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.start.dto.MemberSearchCondition;
import study.querydsl.start.dto.MemberTeamDto;
import study.querydsl.start.dto.QMemberDto;
import study.querydsl.start.dto.QMemberTeamDto;
import study.querydsl.start.entity.Member;
import study.querydsl.start.entity.QMember;
import study.querydsl.start.entity.QTeam;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.start.entity.QMember.*;
import static study.querydsl.start.entity.QTeam.*;

@RequiredArgsConstructor
@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;



    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.of(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findAll_Querydsl() {
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername_Querydsl(String username) {
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition memberCond) {

        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(memberCond.getUsername())){
            builder.and(member.username.eq(memberCond.getUsername()));
        }
        if (hasText(memberCond.getTeamName())) {
            builder.and(team.name.eq(memberCond.getTeamName()));
        }
        if (memberCond.getAgeGoe() != null) {
            builder.and(member.age.goe(memberCond.getAgeGoe()));
        }
        if (memberCond.getAgeLoe() != null) {
            builder.and(member.age.loe(memberCond.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as(team.name)))
                .from(member)
                .join(member.team, team)
                .where(builder)
                .fetch();
    }

    public List<MemberTeamDto> search(MemberSearchCondition condition) {

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId")
                        , team.name.as("teamName") ))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .fetch();
    }


    public List<Member> searchMember(MemberSearchCondition condition) {
        return queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                .fetch();
    }

    private BooleanExpression ageBetween(int ageLoe, int ageGoe) {
        return ageLoe(ageLoe).and(ageGoe(ageGoe));
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username)? member.username.eq(username): null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName)?team.name.eq(teamName):null;
    }
    private BooleanExpression ageGoe(Integer ageGoe) {

        return ageGoe != null ?member.age.goe(ageGoe):null ;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ?member.age.loe(ageLoe):null ;
    }
}
