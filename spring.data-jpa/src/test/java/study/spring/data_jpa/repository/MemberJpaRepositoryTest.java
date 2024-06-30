package study.spring.data_jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.spring.data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@Transactional
@SpringBootTest
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void memberJoinTest(){
        Member member = new Member("jjssaa");

        Member saveMembers = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.findOne(member.getId());


        assertThat(saveMembers.getUsername()).isEqualTo("jjssaa");
        assertThat(saveMembers.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);
        System.out.println("saveMembers = " + saveMembers);
        assertThat((findMember==saveMembers && member == findMember)).isEqualTo(true);
    }
    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> members = memberJpaRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member2);
        long count1 = memberJpaRepository.count();
        assertThat(count1).isEqualTo(1);

        findMember1.setUsername("change success!!");
    }
    @Test
    void namedQueryTest(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> result1 = memberJpaRepository.findByUsername("member1");
        List<Member> result2 = memberJpaRepository.findByUsername("member2");

        log.info("result1 : {}", result1);
        log.info("result2 : {}", result2);
        assertThat(result1.get(0)).isEqualTo(member1);
    }

    @Test
    void paging(){
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));

        int age = 10;
        int offset = 0;
        int limit = 4;
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertThat(members.size()).isEqualTo(4);
        assertThat(totalCount).isEqualTo(6);
    }

    @Test
    void bulkUpdate(){
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 16));
        memberJpaRepository.save(new Member("member3", 29));
        memberJpaRepository.save(new Member("member4", 31));
        memberJpaRepository.save(new Member("member5", 42));
        memberJpaRepository.save(new Member("member6", 27));

        int resultCount = memberJpaRepository.bulkAgePlus(20);//20 살 이상 bulkUpdate 적용 :  4명

        assertThat(resultCount).isEqualTo(4);
    }

}