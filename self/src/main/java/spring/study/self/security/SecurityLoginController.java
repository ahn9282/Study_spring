package spring.study.self.security;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@MapperScan
public class SecurityLoginController {
    @Autowired
    MemberService memberService;

    @RequestMapping("/login")
    public String loginForm(){
        return "/security/login";
    }
    @RequestMapping("/logout")
    public String logOutForm(){
        return "/";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "/security/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto userDto) {

      memberService.insert(userDto);
        log.info("login Controller~~~~ userDto = {}", userDto);
        return "redirect:/register?success";
    }
}
