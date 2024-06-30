package study.spring.data_jpa.controller;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.spring.data_jpa.dto.MemberDto;
import study.spring.data_jpa.entity.Member;
import study.spring.data_jpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //스프링 부트와 스프링 데이터 JPA 에서 이를 결합해서 가능하게 해준다.
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }


    @GetMapping("/members3/{id}")
    public Page<Member> list(Pageable pageable) {
        Page<Member> all = memberRepository.findAll(pageable);
        return   all;
    }

    @GetMapping("/members3s/{id}")
    public Slice<Member> listSlice(@PageableDefault(size = 5) Pageable pageable) {
        PageRequest request = PageRequest.of(1, 2);
        Slice<Member> all = memberRepository.findAll(request);
        Slice<MemberDto> map = all.map(e -> new MemberDto(e.getId(), e.getUsername(), null));
        return   all;
    }

    //@PostConstruct
    public void init() {

        for (int i = 0; i < 20; i++) {
            memberRepository.save(new Member("user" + i));
        }
    }
}
