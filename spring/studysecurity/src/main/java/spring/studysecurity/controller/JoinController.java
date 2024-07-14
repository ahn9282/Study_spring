package spring.studysecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.studysecurity.dto.JoinDTO;
import spring.studysecurity.service.JoinService;

@Controller
@Slf4j
public class JoinController {

    @Autowired
    JoinService joinService;

    @GetMapping("/join")
    public String joinP(Model model) {
        model.addAttribute("joinDTO", new JoinDTO());
        log.info("/login");
        return "/security/register";

    }

    @PostMapping("/joinProc")
    public String joinProcess(@Validated  JoinDTO joinDTO, BindingResult result){
        if(result.hasErrors()){
            log.info("에러 O");
            return "redirect:/join";
        }


        log.info("joinDTO = {}", joinDTO);
        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
