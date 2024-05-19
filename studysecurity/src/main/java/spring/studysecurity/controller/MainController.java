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
import spring.studysecurity.dto.CustomUserDetails;

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

        //CustomUserDetails로 형변환 하고 회원에 대한 추가적인 커스텀한 정보들도 가져오기가 가능하다.
        long pk = (principal instanceof CustomUserDetails) ? ((CustomUserDetails) principal).getUser().getId() : 0L;

        log.info("memeber's pk = {}", pk);
        model.addAttribute("id", id);


        return "main";
    }
    @RequestMapping("/logout")
    public String logOutForm(){
        return "redirect:/login";
    }
}
