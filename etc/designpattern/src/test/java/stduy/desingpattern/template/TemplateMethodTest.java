package stduy.desingpattern.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import stduy.desingpattern.template.code.AbstractTemplate;
import stduy.desingpattern.template.code.SubClassLogic1;
import stduy.desingpattern.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {

        logic1();
        logic2();
    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("비즈니스 로직 1 종료 : resultTime {}", resultTime);
    }
    private void logic2(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("비즈니스 로직 2 종료 : resultTime: {}", resultTime);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }
    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("V2 비즈니스 로직 1 실행");
            }
        };
        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("V2 비즈니스 로직 2 실행");
            }
        };
        log.info("클래스 이름1={}", template1.getClass());
        template1.execute();
        log.info("클래스 이름2={}", template2.getClass());
        template2.execute();

    }

}
