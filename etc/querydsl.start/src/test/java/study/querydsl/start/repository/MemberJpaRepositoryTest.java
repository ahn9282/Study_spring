package study.querydsl.start.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.start.dto.MemberSearchCondition;
import study.querydsl.start.dto.MemberTeamDto;
import study.querydsl.start.entity.Member;
import study.querydsl.start.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberRepository;

    @Test
    void basicTest(){
        Member member = new Member("member1", 15);
        memberRepository.save(member);
        Member fidnMember = memberRepository.findById(member.getId()).get();
        assertThat(fidnMember).isEqualTo(member);

        List<Member> result1 = memberRepository.findAll();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberRepository.findByUsername(member.getUsername());
        assertThat(result2.get(0).getUsername()).isEqualTo("member1");
        assertThat(result2).containsExactly(member);
    }

    @Test
    void basicQuerydslTest() {
        Member member = new Member("member1", 15);
        memberRepository.save(member);
        Member fidnMember = memberRepository.findById(member.getId()).get();
        assertThat(fidnMember).isEqualTo(member);

        List<Member> result1 = memberRepository.findAll_Querydsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberRepository.findByUsername_Querydsl(member.getUsername());
        assertThat(result2.get(0).getUsername()).isEqualTo("member1");
        assertThat(result2).containsExactly(member);
    }
    @Test
    void searchTest(){
            Team teamA = new Team("teamA");
            Team teamB = new Team("teamB");
            em.persist(teamA);
            em.persist(teamB);

            Member member1 = new Member("member1", 15, teamA);
            Member member2 = new Member("member2", 20, teamA);
            Member member3 = new Member("member3", 36, teamB);
            Member member4 = new Member("member4", 45, teamB);


            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            em.flush();
            em.clear();

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(10);
        condition.setAgeLoe(39);
        condition.setTeamName("teamA");
        List<MemberTeamDto> result = memberRepository.searchByBuilder(condition);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUsername()).isEqualTo("member1");
        assertThat(result.get(1).getUsername()).isEqualTo("member2");

    }
    @Test
    void searchBooleanExpressionTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 36, teamB);
        Member member4 = new Member("member4", 45, teamB);


        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(10);
        condition.setAgeLoe(39);
        condition.setTeamName("teamA");
        List<MemberTeamDto> result = memberRepository.search(condition);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUsername()).isEqualTo("member1");
        assertThat(result.get(1).getUsername()).isEqualTo("member2");
        log.info("result : {}", result);

    }

}