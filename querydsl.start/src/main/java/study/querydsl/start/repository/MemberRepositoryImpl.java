package study.querydsl.start.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.start.dto.MemberSearchCondition;
import study.querydsl.start.dto.MemberTeamDto;
import study.querydsl.start.dto.QMemberTeamDto;
import study.querydsl.start.entity.Member;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.start.entity.QMember.member;
import static study.querydsl.start.entity.QTeam.team;

public class MemberRepositoryImpl   implements MemberRepositoryCustom {
                                //이를 사용할 경우 이전버전꺼라 from이 처음이고 select가 맨뒤로 간다.
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager){
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
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

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results =queryFactory
                .select(new QMemberTeamDto(
                member.id,
                member.username,
                member.age,
                team.id,
                team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .orderBy(member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }



    @Override
        public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition,Pageable pageable) {

            List<MemberTeamDto> content =queryFactory
                    .select(new QMemberTeamDto(
                member.id.as("memberId"),
                member.username,
                member.age,
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

            JPQLQuery<Long> countQuery =queryFactory
                    .select(member.count())
                    .from(member)
                    .leftJoin(member.team, team)
                    .where(
                            usernameEq(condition.getUsername()),
                            teamNameEq(condition.getTeamName()),
                            ageGoe(condition.getAgeGoe()),
                            ageLoe(condition.getAgeLoe())
                    )
                    .select(member.count());
            return PageableExecutionUtils.getPage(content, pageable,
                    countQuery::fetchOne);
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
