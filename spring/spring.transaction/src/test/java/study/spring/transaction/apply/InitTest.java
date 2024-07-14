package study.spring.transaction.apply;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class InitTest {
    @Autowired
    Hello hello;

    @Test
    void go(){
        //초기화 코드는 스프링이 초기화 시점에 실행

    }

    @TestConfiguration
    static class InitTestConfig {
        @Bean
        public Hello hello(){
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {
        @PostConstruct
        @Transactional
        public void initV1() {
            //@PostConstruct 같인 초기화 코드에 @Transactional 적용 안됨
            //순서가 @PostConstruct 가 우선이여서 트래잭션 적용 x
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init PostConstructor Active = {}", isActive);
        }

        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init PostConstructor Active = {}", isActive);
        }
}
}
