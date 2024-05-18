package spring.study.self.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecurityController {

    @GetMapping("/user")
    public String userSecurity() {
        return "ok";
    }

    @GetMapping("/security-test")
    public ResponseEntity<SecurityTestDto> testSecurity(@RequestParam boolean tf) {

        SecurityTestDto testDto = new SecurityTestDto();

        testDto.setTestOk(tf);
        return ResponseEntity.ok(testDto);

    }
}
