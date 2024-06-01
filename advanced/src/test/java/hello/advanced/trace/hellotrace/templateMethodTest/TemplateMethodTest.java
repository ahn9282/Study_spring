package hello.advanced.trace.hellotrace.templateMethodTest;

import hello.advanced.trace.hellotrace.templateMethodTest.code.AbstractTemplate;
import hello.advanced.trace.hellotrace.templateMethodTest.code.SubClassLogicV1;
import hello.advanced.trace.hellotrace.templateMethodTest.code.SubClassLogicV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodTestV0() {
        logic1();
        logic2();

    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 1 실행");
        //비즈니스 로직 1 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("비즈니스 로직 1 종료 {} ", resultTime);

    }   private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 2 실행");
        //비즈니스 로직 1 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("비즈니스 로직 2 종료 {} ", resultTime);

    }

    @Test
    void TemplateMethodV1() {
        AbstractTemplate template1 = new SubClassLogicV1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogicV2();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call(){
                log.info("비즈니스 로직 1 실행");
            }
        };
        log.info("클래스 이름 1 = {}", template1.getClass());
        template1.execute();
        AbstractTemplate template2 = new AbstractTemplate() {

            @Override
            protected void call(){
                log.info("비즈니스 로직 2 실행");
            }
        };
        log.info("클래스 2 이름 = {}", template2.getClass());
        template2.execute();
    }
}
