package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {

        Member member = new Member();
        member.setName("an");

        Long saveId = memberService.join(member);
        em.flush(); // 이는 현재 영속성 컨테이너를 반영하여 db에 등록 시킨다. 현 기준 일괄 처리
        //대충 이 시점을 체크포인트로 한다고 보면 된다.

        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kin1");
        Member member2 = new Member();
        member2.setName("kin1");

        memberService.join(member1);
        try {

            memberService.join(member2);//중복 예외 발생
        } catch (IllegalStateException e) {
            return;
        }

        fail("예외가 발생해야 함./");



    }

}