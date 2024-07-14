package self.study.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import self.study.jwt.dto.JoinForm;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class JoinServiceTest {

    @Autowired
   private JoinService joinService;

    @Test
    void joinProcess() {
        JoinForm form = new JoinForm();

        form.setUsername("qwer");
        form.setPassword("1111");

        joinService.joinProcess(form);


    }
}