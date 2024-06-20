package hello.jpa.study2.api;

import hello.jpa.study2.domain.Member;
import hello.jpa.study2.service.MemberService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

        Long join = memberService.join(member);
      return  new CreateMemberResponse(join);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());
        Long join = memberService.join(member);
        return new CreateMemberResponse(join);
    }


    @Data
    static class CreateMemberRequest{
        private String name;

        public CreateMemberRequest() {
        }

        public CreateMemberRequest(String name) {
            this.name = name;
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;

    }
}
