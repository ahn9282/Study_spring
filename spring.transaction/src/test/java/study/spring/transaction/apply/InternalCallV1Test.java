package study.spring.transaction.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    CallService callService;
    @Autowired
    InternalService internalService;
    @Test
    void printProxy(){
        log.info("callService call = {}", callService.getClass());

    }
    @Test
    void internalCall(){
        internalService.internal();

    }
    @Test
    void externalCall(){
        callService.external();

    }
    @Test
    void externalCallV2(){
        callService.external();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig{
        @Bean
        CallService callService(){
            return new CallService(internalCallService());
        }
        @Bean
        InternalService internalCallService(){
            return new InternalService();
        }
    }
    @RequiredArgsConstructor
    static class CallService {
        private final InternalService internalService;

        public void external(){
            log.info("call external");
            printTxInfo();
            internalService.internal();
            // 이경우 external 은 proxy가 적용되지 않아 proxy가 아닌 구현체의 this.internal이 실행됨
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }
    }
    static class InternalService{
        public void external(){
            log.info("call external");
            internal();
            // 이경우 external 은 proxy가 적용되지 않아 proxy가 아닌 구현체의 this.internal이 실행됨
        }
        //@Transactional 의 경우 주로 비즈니스 로직 시작점에서 쓰므로 public 메서드에만 실행된다.
        @Transactional
        public void internal(){
            log.info("call internal");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }
    }
}
