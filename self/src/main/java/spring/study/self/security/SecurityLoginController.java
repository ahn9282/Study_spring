package spring.study.self.security;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class SecurityLoginController {

    @RequestMapping("/login")
    public String loginForm(){
        return "login.html";
    }
    @RequestMapping("/logout")
    public String logOutForm(){
        return "/";
    }
}
