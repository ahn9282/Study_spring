package study.spring.data_jpa.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.spring.data_jpa.dto.MemberDto;
import study.spring.data_jpa.dto.UsernameOnly;
import study.spring.data_jpa.dto.UsernameOnlyDto;
import study.spring.data_jpa.entity.Member;
import study.spring.data_jpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.assertj.core.api.Assertions.*;


@Slf4j
@Transactional
@SpringBootTest
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    void testMemver(){
        Member member = new Member("kkk");

        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    }
    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member2);
        long count1 = memberRepository.count();
        assertThat(count1).isEqualTo(1);

        findMember1.setUsername("change success!!");
    }


    @Test
    void methodMakerTest() {
        int limit = 100;
        Member member = new Member("kk");
        member.setAge(15);
        memberRepository.save(member);
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThanAndAgeLessThan(member.getUsername(), member.getAge()-1, limit);
        log.info("result : {}", result);
        List<Member> result2 = memberRepository.findTop3HelloBy();
        log.info("result2 : {}", result2);

    }
    @Test
    void namedQueryTest(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result1 = memberRepository.findByUsername("member1");
        List<Member> result2 = memberRepository.findByUsername("member2");

        log.info("result1 : {}", result1);
        log.info("result2 : {}", result2);
        assertThat(result1.get(0)).isEqualTo(member1);
    }
    @Test
    void testAnnotationQuery() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> user = memberRepository.findUser(member1.getUsername(), member1.getAge());
        assertThat(user.get(0)).isEqualTo(member1);
    }

    @Test
    void findUsernameTest() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<String> result = memberRepository.findUsernameList();
        assertThat(result.get(0)).isEqualTo(member1.getUsername());
        assertThat(result.get(1)).isEqualTo(member2.getUsername());
    }
    @Test
    void findMemberDtoTest() {

        Team team = new Team("teamA");
        teamRepository.save(team);

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        member1.setTeam(team);
        member2.setTeam(team);
        memberRepository.save(member1);
        memberRepository.save(member2);


        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto member : memberDto) {
            log.info("member : {}", member);
        }
        assertThat(memberDto.size()).isEqualTo(2);
    }

    @Test
    void findByUserNamesTest() {

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> names = Arrays.asList("member1", "member2");

        List<Member> list = memberRepository.findByNames(names);
        assertThat(list.get(0).getUsername()).isEqualTo(member1.getUsername());
        assertThat(list.get(1).getUsername()).isEqualTo(member2.getUsername());
    }

    @Test
    void pageableTest() {

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));

        int age = 10;
        PageRequest pageR = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        //pageable 구현체 start, amount, sorting 모두 설정한다음 파라미터에 넣고 Page<T> 객체로 반환하면된다.



        Slice<Member> result = memberRepository.findByAge(age, pageR);
        //Slice 의 경우 Total Count 쿼리를 보내지 않아 없지만, limit + 1을 통해 마지막 페이지인지 아닌지 여부를 알 수 있다.
        //그래서 마지막 페이지인지 여부를 확인 가능과 count 쿼리문을 보내지 않아 성능상 유리하고 내 생각에도 더 권장될 것 같다.

        //그리고 엔티티를 직접 노출시키는 일은 막아야한다.
        Slice<MemberDto> memberDtos = result.map(e -> new MemberDto(e.getId(),e.getUsername(), null)) ;

        log.info("result : {}", result);
        log.info("list : {}", result.getContent());
       // log.info("totalCount : {}", result.getTotalElements());
       // log.info("totalPage : {}", result.getTotalPages());
        assertThat(result.getContent().size()).isEqualTo(3);
       // assertThat(result.getTotalElements()).isEqualTo(6);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isFalse();
        assertThat(result.hasNext()).isTrue();
    }

    @Test
    void bulkUpdate(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 16));
        memberRepository.save(new Member("member3", 29));
        memberRepository.save(new Member("member4", 31));
        memberRepository.save(new Member("member5", 42));
        memberRepository.save(new Member("member6", 27));

        int resultCount = memberRepository.bulkAgePlus(20);//20 살 이상 bulkUpdate 적용 :  4명

        List<Member> result = memberRepository.findByUsername("member5");
        Member nowMember5 = result.get(0);
        //이 경우 db에는 bulk연산을 통해 적용되었지만, 영속성 컨텍스트는 아직 그대로 이기에 서로 안맞는 문제가 생긴다.
        assertThat(nowMember5.getAge()).isEqualTo(42);//늘어나지 않음
        em.flush();
        em.clear();
        List<Member> newMember5 = memberRepository.findByUsername("member5");
        assertThat(newMember5.get(0).getAge()).isEqualTo(43);//영속성 컨텍스트 비우고 재시도
        

        assertThat(resultCount).isEqualTo(4);
    }
    @Test
    void findMemberLazy(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member1",16,teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();
        List<Member> result = memberRepository.findEntityGraphByUsername("member1");
        List<Member> result2 = memberRepository.findMemberEntityGraph();

        assertThat(result).isEqualTo(members);
        assertThat(result).isEqualTo(result2);

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("-> member's teamClass = " + member.getTeam().getClass());
            System.out.println("-> member's teamName = " + member.getTeam().getName());
        }
    }

    @Test
    void queryHint(){
        Member member1 = memberRepository.save(new Member("member1", 15));
        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("member1");

        findMember.setUsername("member2");

        em.flush();
    }

    @Test
    public void lock(){
        Member member1 = memberRepository.save(new Member("member1", 15));
        em.flush();
        em.clear();

        List<Member> result = memberRepository.findLockByUsername("member1");
    }

    @Test
    void callCustom() {
        List<Member> memberCustom = memberRepository.findMemberCustom();

    }
    @Test
    public void JpaEventBaseEntity() throws InterruptedException {
        Member member = new Member("member1");
        Member save = memberRepository.save(member);//@PrePersist

        Thread.sleep(100);

        save.setUsername("member2");//@PreUpdate
        em.flush();
        em.clear();
        Member findMember = memberRepository.findById(save.getId()).get();

        System.out.println("findMember's createdDate = " + findMember.getCreatedDate());
        System.out.println("findMember's updatedDate = " + findMember.getLastModifiedDate());
        System.out.println("findMember's createdBy = " + findMember.getCreatedBy());
        System.out.println("findMember's updatedBy = " + findMember.getModifiedBy());

    }

    @Test
    void specificationBasic(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 15, teamA);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();

        em.clear();
        memberRepository.findAll();
    }

    @Test
    void queryByExample(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 15, teamA);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        Member member = new Member("member1");
        Team team = new Team("teamA");
        //도메인 객체 자체를 지정
        member.setTeam(team);//연관 관계 setting을 통해 동시 조건으로 추가

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age");//날짜는 Example에서 제외하도록 설정
        //이를 안할 경우 default로 0 이거나 Null로 조건에 도메인의  모든 필드들을 조건에 포함시키기 때문에
        //위를 통해 무시 설정을 해줘야한다.

        Example<Member> memberExample = Example.of(member, matcher);
        List<Member> result = memberRepository.findAll(memberExample);

        if(result.isEmpty()) {
            assertThat(result.isEmpty()).isTrue();
            log.info("없음");
        }else {
            assertThat(result.get(0).getUsername()).isEqualTo("member1");
            log.info("result : {}", result);
        }
    }
    @Test
    public void projectionTest(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 15, teamA);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();

        List<NestedClosedProjections> result = memberRepository.findProjectionByUsername("member1", NestedClosedProjections.class);

        for (NestedClosedProjections  member : result) {
            String username = member.getUsername();
            String s = member.getTeam().getName();
            System.out.println("username = " + username);
            System.out.println("team = " + s);
        }
    }
    @Test
    void nativeQueryT(){
        em.clear();  Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 15, teamA);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        Member result = memberRepository.findByNativeQuery("member2");
        assertThat(result.getUsername()).isEqualTo("member2");
    }
    @Test
    void nativeProjectionsQueryT(){
        em.clear();  Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 15, teamA);
        Member member2 = new Member("member2", 15, teamA);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        Page<MemberProjection> result = memberRepository.findByNativeProjection(PageRequest.of(0, 5));
        System.out.println("result = " + result);
        List<MemberProjection> content = result.getContent();
        for (MemberProjection memberProjection : content) {
            System.out.println("memberProjection = " + memberProjection);
            System.out.println("-> userName = " + memberProjection.getUsername());
            System.out.println("-> teamName = " + memberProjection.getTeamName());
        }


    }

}
