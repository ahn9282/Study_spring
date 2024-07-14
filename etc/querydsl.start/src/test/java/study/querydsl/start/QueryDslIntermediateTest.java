package study.querydsl.start;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.start.dto.MemberDto;
import study.querydsl.start.dto.QMemberDto;
import study.querydsl.start.dto.UserDto;
import study.querydsl.start.entity.Member;
import study.querydsl.start.entity.QMember;
import study.querydsl.start.entity.Team;

import java.util.List;
import static study.querydsl.start.entity.QMember.*;

@SpringBootTest
@Transactional
public class QueryDslIntermediateTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;
    @BeforeEach
    void before(){
        this.queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }

    @Test
    void eimpleProjection(){
        List<String> results = queryFactory
                .select(member.username)
                .from(member)
                .fetch();
        for (String result : results) {
            System.out.println("result = " + result);
        }
    }
    @Test
    void tupleProjection(){
        List<Tuple> results = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();
        for (Tuple result : results) {
            System.out.print("result name = " + result.get(member.username) + "\t");
            System.out.println("result age = " + result.get(member.age));
        }
    }

    //Dto 로 반환 : 반환하는 Dto는 기본 NoArgs 생성자로 생성후 반환하기에 기본 생성자 필 수
    @Test
    void findDtoByQueryDsL() {
        List<MemberDto> results = queryFactory
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto result : results) {
            System.out.println("result = " + result);
        }
    }
    //field에 맞춰서 값을 맵핑한다. getter, setter 필요 X
    @Test
    void findDtoByField() {
        List<MemberDto> results = queryFactory
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto result : results) {
            System.out.println("result = " + result);
        }
    }
    //field 와 alias 를 맞춰져야 한다. -> UserDto {name, age}
    @Test
     void findUserDto() {
        QMember memberSub = new QMember("memberSub");
        List<UserDto> results = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name") ,
                        ExpressionUtils.as(JPAExpressions // 서브쿼리 별칭 지정 ExpressionUtils.as(서브쿼리, 별칭)
                                .select(memberSub.age.max())
                                .from(memberSub),"age")))
                .from(member)
                .fetch();

        for (UserDto result : results) {
            System.out.println("result = " + result);
        }
    }
    //생성자 방식의 경우 변수명이 아니라 타입을 기준으로 받아서 순서만 맞으면 아아서 맵핑됨
    @Test
    void findDtoByConstructor(){
        List<UserDto> results = queryFactory
                .select(Projections.constructor(UserDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
        for (UserDto userDto: results) {
            System.out.println("userDto = " + userDto);
        }
    }
    @Test
    //@QueryProjection 을 통해 안정적으로 Dto로 반환이 된다.
    void findDtoByQueProjection(){
        List<MemberDto> results = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto result : results) {
            System.out.println("result = " + result);
        }

    }

    /*
     * querydsl 동적쿼리 BooleanBuilder -> if 역할과 비슷함 조건에 해당하면 해당 쿼리문 추가
     * */
    @Test
    void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> results = searchMember1(usernameParam, ageParam);
        Assertions.assertThat(results.size()).isEqualTo(1);
    }
    /*
    * where 다중 파라미터 동ㅈ거쿼리
    * */
    @Test
    void dynamicQuery_WhereParam() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> results = searchMember2(usernameParam, ageParam);
        Assertions.assertThat(results.size()).isEqualTo(1);
    }

    //벌크 연산 -> db 값을 일괄로 delete, update 하는 연산을 뜻함
    @Test
    void bulkUpdate(){
        //member1 = 10 -> 비회원
        //member2 = 20 -> 비회원
        //member3 = 30 -> 유지
        //member4 = 40 -> 유지

        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();
        for (Member member1 : result) {
            System.out.println("member = " + member1);
        }
    }
    @Test
    void bulkAdd() {
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();
        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();
        for (Member member1 : result) {
            System.out.println("member = " + member1);
        }
    }

    @Test
    void bulkDelete(){
        long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();

        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();
        for (Member member1 : result) {
            System.out.println("member = " + member1);
        }
    }

    //SQL Function 호출
    @Test
    void sqlFunction(){
        List<String> results = queryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                        member.username, "member", "M")
                )
                .from(member)
                .fetch();
        for (String result : results) {
            System.out.println("result = " + result);
        }
    }

    @Test
    void sqlFunction2() {
        List<String> results = queryFactory
                .select(member.username)
                .from(member)
                .where(member.username.eq(member.username.lower()))
                .fetch();
        for (String result : results) {
            System.out.println("result = " + result);
        }
    }

    //===============동적 쿼리에 사용된 메서드들 : 이렇게 분리하면 재사용성이 높아짐=====================
    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond == null ? null : member.username.eq(usernameCond);
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond == null ? null : member.age.eq(ageCond);
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }
}
