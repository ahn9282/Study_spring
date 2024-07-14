package study.ajs_1.loginstudy.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.ajs_1.loginstudy.domain.Member;
import study.ajs_1.loginstudy.domain.MemberRepository;

import javax.print.attribute.standard.PresentationDirection;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {

        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult result) {
        if (result.hasErrors()) {

            result.rejectValue("name","required",new Object[]{6,15},null);
            return "members/addMemberForm";

        }

        memberRepository.save(member);
        return "redirect:/";
    }
}
