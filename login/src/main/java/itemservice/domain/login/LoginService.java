package itemservice.domain.login;

import itemservice.domain.member.Member;
import itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    //@return null   로그인 실패
    public Member login(String loginId, String password) {
        /*Optional<Member> findMemberOptional = memberRepository.findByLongId(loginId);
        Member member = findMemberOptional.get();
        if (member.getPassword().equals(password)) {
            return member;
        }else{
            return  null;
        }*/
        //람다와 스트림으로 이렇게 코드 단축
        return memberRepository.findByLongId(loginId)
                                    .filter(m -> m.getPassword().equals(password))
                                    .orElse(null);

    }

}
