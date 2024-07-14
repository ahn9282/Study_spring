package study.jwt.more.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.jwt.more.dto.JoinForm;
import study.jwt.more.service.JoinService;

@Slf4j
@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinForm form) {
        log.info("login data : {}", form);
        joinService.joinProcess(form);

        return "ok";
    }
}
