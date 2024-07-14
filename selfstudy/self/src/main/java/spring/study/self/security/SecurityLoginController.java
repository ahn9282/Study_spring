package spring.study.self.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@MapperScan
@RequiredArgsConstructor
public class SecurityLoginController {
    @Autowired
    MemberService memberService;
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String loginForm(){

        return "/security/login";
    }

    @PostMapping("/login") // POST 요청을 처리하도록 변경
    public String loginForm(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password);
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return "redirect:/";
        } catch (AuthenticationException e) {
            return "redirect:/login?error";
        }
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
