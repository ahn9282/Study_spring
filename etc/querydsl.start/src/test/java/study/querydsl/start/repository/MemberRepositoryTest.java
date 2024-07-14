package study.querydsl.start.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.start.dto.MemberSearchCondition;
import study.querydsl.start.dto.MemberTeamDto;
import study.querydsl.start.entity.Member;
import study.querydsl.start.entity.QMember;
import study.querydsl.start.entity.Team;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.querydsl.start.entity.QMember.member;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void basicTest(){
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());

        List<Member> result = memberRepository.findByUsername("member1");
        assertThat(result.get(0).getUsername()).isEqualTo("member1");
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


        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

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

    }

    @Test
    void searchPAgeSimpleTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 36, teamB);
        Member member4 = new Member("member4", 45, teamB);


        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        em.flush();
        em.clear();

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition,pageRequest);
        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username").containsExactly("member1","member2","member3");

    }

    @Test
    void querydslPredicateExecutorTest() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 36, teamB);
        Member member4 = new Member("member4", 45, teamB);


        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        em.flush();
        em.clear();
        QMember member = QMember.member;
        Iterable<Member> result = memberRepository.findAll(member.age.between(19, 46).and(member.username.startsWith("member4")));
        for (Member memberResult : result) {
            System.out.println("memberResult = " + memberResult);
        }
    }
}