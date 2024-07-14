package study.spring.transaction.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
@Slf4j
public class TxBasicTest {
    @Autowired
    BasicService basicService;

    @Test
    void proxyCheck(){
        log.info("aop class = {}", basicService.getClass());
        Assertions.assertThat(AopUtils.isAopProxy(basicService)).isTrue();
                    //aopProxy 인지를 boolean 반환 메서드 @Transactional의 경우 aopproxy를 적용한다.
    }

    @Test
    void txTest(){
        basicService.tx();
        basicService.nonTx();
        
    }

    @TestConfiguration
    static class TxApplyBasicConfig {

        @Bean
        BasicService basicService(){
            return new BasicService();
        }
    }


    @Slf4j
    static class BasicService {


        @Transactional
        public void tx(){
            log.info("call tx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            //트랜잭션 on/off 여부 boolean 반환
            log.info("tx active = {}", txActive);
        }
        public void nonTx(){
            log.info("call nonTx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }
    }
}
