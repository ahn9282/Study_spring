package spring.study.self.security;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class SecurityLoginController {

    @RequestMapping
    public String loginForm(){
        return "login.html";
    }
}
