package hello.jpa.study2.api;

import hello.jpa.study2.domain.Member;
import hello.jpa.study2.service.MemberService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponse(@PathVariable("id") Long id,
                                                     @RequestBody @Valid UpdateMemberRequest request) {

      memberService.update(id, request.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {

        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> members = findMembers.stream()
                .map(e -> new MemberDto(e.getName()))
                .collect(Collectors.toList());


        return new Result(members);
    }

    @Data@AllArgsConstructor
    static class Result<T>{
        private T data;

    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
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
