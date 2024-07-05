package study.spring.transaction.exception;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService rollbackService;

    @Test
    void runtimeException(){
        assertThrows(RuntimeException.class, () ->
                rollbackService.runtimeException()
        );

    }
    @Test
    void checkedException() throws MyException {
        assertThrows(MyException.class, () ->
                rollbackService.checkedException()
        );
    }
    @Test
    void rollbackForMyException() throws MyException {
        assertThrows(MyException.class, () ->
                rollbackService.rollbackForMyException()
        );
    }

    @TestConfiguration
    static class TestRollbackConfig{

        @Bean
        public RollbackService rollbackService(){
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService {

        //런타임 예외 발생 : 롤백
        @Transactional
        public void runtimeException() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        // 체크 예외 발생 : 커밋
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }

        //체크 예외 rollbackFor 지정 : 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackForMyException() throws MyException {
            log.info("rollbackForMyException");
            throw new MyException();
        }
    }

    static class MyException extends Exception {
    }
}
