package spring.studysecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/")
    public String mainP( Model model){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //SecurityContextHolder -> SecurityContext 에서 Authentication 객체를 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? "" : authorities.iterator().next().getAuthority();

        model.addAttribute("role", role);

        Object principal = authentication.getPrincipal();
        String id = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "";

        model.addAttribute("id", id);


        return "main";
    }
    @RequestMapping("/logout")
    public String logOutForm(){
        return "redirect:/login";
    }
}
