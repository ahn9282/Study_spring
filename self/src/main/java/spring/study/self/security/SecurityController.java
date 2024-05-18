package spring.study.self.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SecurityController {

    @GetMapping("/user")
    public ResponseEntity userSecurity(@AuthenticationPrincipal UserDetails userDetails) {

        return new ResponseEntity<>(userDetails.getUsername(), HttpStatus.OK);

    }
    @GetMapping("/user-info")
    public UserDetails userInfoSecurity() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        log.info("username = {}, password = {}", username, password);

       return userDetails;

    }


    @GetMapping("/security-test")
    public ResponseEntity<SecurityTestDto> testSecurity(@RequestParam boolean tf) {

        SecurityTestDto testDto = new SecurityTestDto();

        testDto.setTestOk(tf);
        return ResponseEntity.ok(testDto);

    }

}
