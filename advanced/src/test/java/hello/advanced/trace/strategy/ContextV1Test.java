package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ContextV1Test {

    @Test
    void strategyTestV4(){

        ContextV1 contextx = new ContextV1(() -> log.info("비즈니스 로직 x 실행"));

        ContextV1 context1 = new ContextV1(new StrategyLogic1() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행!~");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new StrategyLogic2() {
            @Override
            public void call() {
                log.info("비즈니스 로직 2 실행!~");
            }
        });
        context2.execute();

    }

    @Test
    void strategyTestV3() {

        ContextV1 context1 = new ContextV1(new StrategyLogic1() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행!~");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new StrategyLogic2() {
            @Override
            public void call() {
                log.info("비즈니스 로직 2 실행!~");
            }
        });
        context2.execute();

    }
    @Test
    void strategyTestV2(){
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행!~");
            }
        };
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행!~");
            }
        });
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2() {
            @Override
            public void call() {
                log.info("비즈니스 로직 2 실행!~");
            }
        };
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
        
    }
    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyTestV1(){
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        hello.advanced.trace.strategy.code.strategy.ContextV1 context1 = new hello.advanced.trace.strategy.code.strategy.ContextV1(strategyLogic1);
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        hello.advanced.trace.strategy.code.strategy.ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();

    }
   @Test
    void strategyV0() {
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


}