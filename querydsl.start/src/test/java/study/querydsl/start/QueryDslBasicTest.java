package study.querydsl.start;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.start.entity.Member;
import study.querydsl.start.entity.QMember;
import study.querydsl.start.entity.QTeam;
import study.querydsl.start.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.querydsl.start.entity.QMember.*;
import static study.querydsl.start.entity.QTeam.*;

@SpringBootTest
@Transactional
public class QueryDslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory  queryFactory;

    @Test
    void startJPQL(){
        List<Member> members = em.createQuery("select m from Member m ", Member.class)
                .getResultList();
        assertThat(members.size()).isEqualTo(4);
        assertThat(members.get(0).getUsername()).isEqualTo("member1");
    }
    @Test
    void startQuerydsl() {
        queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");
        Member findMember = queryFactory.select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
    @Test
    void startQuerydsl2() {
        Member findMember = queryFactory.select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search(){
        queryFactory = new JPAQueryFactory(em);
        Member findMember = queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(
                        member.username.eq("member1")
                                .and(member.age.loe(30))
                                .or(team.name.eq("teamA"))
                )
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
    @Test
    void resultFetch(){
        queryFactory = new JPAQueryFactory(em);
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        Member fetchOne = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1."))
                .fetchOne();

        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst();

        //Page<T> 로 봐도 무방
        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();
        long totalCount = queryFactory
                .selectFrom(member)
                .fetchCount();

        long total = results.getTotal();
        List<Member> content = results.getResults();
        long offset = results.getOffset();
        long limit = results.getLimit();
    }
    @Test
    void sort(){
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        queryFactory = new JPAQueryFactory(em);
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();
        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();

    }
    @Test
    void paging1(){

        queryFactory = new JPAQueryFactory(em);
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();
        assertThat(result.size()).isEqualTo(2);
    }
    @Test
    void paging2(){

        queryFactory = new JPAQueryFactory(em);
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    //집합 함수 group by , having
    @Test
    void aggregation(){
        queryFactory = new JPAQueryFactory(em);
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetch();
        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(169);
        assertThat(tuple.get(member.age.avg())).isEqualTo(42.25);
        assertThat(tuple.get(member.age.max())).isEqualTo(126);
        assertThat(tuple.get(member.age.min())).isEqualTo(13);

    }
    /*
    * 팀의 이름과 각 팀의 평균 연령을 구해라.
    * */
    @Test
    void group(){
        queryFactory = new JPAQueryFactory(em);
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(14);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(70.5);
    }

    /*
    * 팀 A 소속된 모든 회원
    * */
    @Test
    void join(){
        queryFactory = new JPAQueryFactory(em);
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();
        assertThat(fetch.size()).isEqualTo(2);
        assertThat(fetch)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    void theta_join() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        queryFactory = new JPAQueryFactory(em);
        queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

    }
    /*
    * 회원과 팀을 조이하면서, 팀이름이 teamA인 팀만 조인, 회원은 모두 조회
    * JPQL : select m, t from Member m left join m.team t on t.name = 'teamA'
    * */
    @Test
    void join_on_filtering(){
        queryFactory = new JPAQueryFactory(em);
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

    }
    /*
    * 연관관계 없는 엔티티 외부 조인
    * 회원의 이름이 팀 이름과 같은 회원
    * */
    @Test
    public void join_on_no_relation(){
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        queryFactory = new JPAQueryFactory(em);
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(team.name.eq(member.username))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }
    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void fetchJoinNO() {
        em.flush();
        em.clear();
        queryFactory = new JPAQueryFactory(em);
        Member member1 = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(member1.getTeam());
        assertThat(loaded).as("페치 조인 미적용").isFalse();
    }
    @Test
    void fetchJoinYes() {
        em.flush();
        em.clear();
        queryFactory = new JPAQueryFactory(em);
        Member member1 = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(member1.getTeam());
        assertThat(loaded).as("페치 조인 미적용").isTrue();
    }

    /*
    * 나이가 가장 많은 회원 조회
    * */
    @Test
    void suvQuery(){
        queryFactory = new JPAQueryFactory(em);
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(126);
    }

    /*
     * 10 살 초과인 회원 조회
     * */
    @Test
    void suvQuery2(){
        queryFactory = new JPAQueryFactory(em);
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.loe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(15,13,15);
    }


    /*
     * 나이가 평균 10살 이상 회원 조회
     * */
    @Test
    void suvQuery3(){
        queryFactory = new JPAQueryFactory(em);
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(15,13,126, 15);
    }

    @Test
    void selectSubQuery(){
        QMember memberSub = new QMember("memberSub");
        queryFactory = new JPAQueryFactory(em);
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(18))
                )).fetch();
        for (Member member : result) {
            System.out.println("member = " + member);
        }
        assertThat(result).extracting("age")
                .containsExactly(126);
    }

    /*
    * case ~ when 문 사용
    * */
    @Test
    void basicCase(){
        queryFactory = new JPAQueryFactory(em);
        List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
    @Test
    void complexCase(){
        queryFactory = new JPAQueryFactory(em);
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(10, 20)).then("10대")
                        .when(member.age.between(21, 200)).then("성인")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("연령 = " + s);
        }

    }

    @Test
    void constant(){
        queryFactory = new JPAQueryFactory(em);
        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }
    @Test
    //query dsl 에서 형변환은  .stringValue()로 한다. <- 쓸일이 상당히 많다.
    void concat(){
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("result = " + s);
        }

    }

    @BeforeEach
    void before(){
        this.queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 13, teamA);
        Member member3 = new Member("member3", 126, teamB);
        Member member4 = new Member("member4", 15, teamB);


        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }
}
