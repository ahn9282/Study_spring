package study.spring.aop.exam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import study.spring.aop.exam.aop.CoverAspect;
import study.spring.aop.exam.aop.RetryAspect;
import study.spring.aop.exam.aop.TraceAspect;

@Slf4j
@SpringBootTest
@Import({TraceAspect.class, RetryAspect.class})
public class ExamTest {

    @Autowired
    ExamService examService;

    @Test
    void test(){
        for (int i = 0; i < 5; i++) {
            log.info("client request i = {}", i);
            examService.request("data" + 1);
        }
    }
}
