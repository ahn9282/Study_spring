package self.study.jwt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import self.study.jwt.dto.JoinForm;
import self.study.jwt.service.JoinService;

import java.io.EOFException;

@RestController
@Slf4j
public class JoinController {

    @Autowired
    private JoinService joinService;


    @PostMapping("/join")
    public String joinProcess(@RequestParam String username, @RequestParam String password) {
        try {

            JoinForm form = new JoinForm(username, password);

            log.info("username = {}", form.getUsername());
            log.info("password={}", form.getPassword());
            joinService.joinProcess(form);
            return "success!";

        } catch (Exception e) {
            log.info("예외 발생");
                    return "failed...";
        }
    }

}
