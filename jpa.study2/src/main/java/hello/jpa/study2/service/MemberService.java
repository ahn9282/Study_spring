package hello.jpa.study2.service;

import hello.jpa.study2.repository.MemberRepository;
import hello.jpa.study2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional 필수인 JPA에서 조회하는 기능마다 이렇게 해주면 JPA가 조회의 경우 최적화기능 제공
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;



    //회원 가입
    @Transactional // 이렇게 따로 @Transactional 명시하면 readOnly false로 덧씌워짐
    public Long join(Member member) {

        validateDuplicateMember(member);//중복 회원 검증
        repository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =  repository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return repository.findAll();
    }
     //회원 한명 조회
    public Member findOne(Long memberId) {
        return repository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = repository.findOne(id);
        member.setName(name);
    }
}
