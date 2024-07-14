package stduy.desingpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import stduy.desingpattern.strategy.code.strategy.ContextV1;
import stduy.desingpattern.strategy.code.strategy.Strategy;
import stduy.desingpattern.strategy.code.strategy.StrategyLogic1;
import stduy.desingpattern.strategy.code.strategy.StrategyLogic2;
import stduy.desingpattern.template.code.AbstractTemplate;
import stduy.desingpattern.template.code.SubClassLogic1;
import stduy.desingpattern.template.code.SubClassLogic2;

import javax.naming.Context;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {

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
    void strategyV1(){
        StrategyLogic1 logic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(logic1);
        contextV1.execute();

        StrategyLogic2 logic2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(logic2);
        contextV2.execute();

    }
    /**
     * 익명 내부 클래스
     */
    @Test
    void strategyV2(){
        Strategy strategy = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행");
            }
        };
        ContextV1 c1 = new ContextV1(strategy);
        c1.execute();

        Strategy strategy2 = () -> log.info("비즈니스 로직 2 실행");
        ContextV1 c2 = new ContextV1(strategy2);
        c2.execute();

    }
    @Test
    void strategyV3(){

        ContextV1 c1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행");
            }
        });
        c1.execute();

        ContextV1 c2 = new ContextV1(() -> log.info("비즈니스 로직 2실행"));
    }

}
