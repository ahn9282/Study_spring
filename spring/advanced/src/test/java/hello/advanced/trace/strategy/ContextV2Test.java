package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import hello.advanced.trace.strategy.code.strategy.ContextV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV2Test {

    @Test
    void strategyV1(){
        ContextV2 context1 = new ContextV2();
        context1.execute(new StrategyLogic1());
        ContextV2 context2 = new ContextV2();
        context2.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        ContextV2 context1 = new ContextV2();
        context1.execute(new Strategy() {
            @Override
            public void call() {
                log.info("로직 1을 내부 익명함수로 실행");
            }
        });
        ContextV2 context2 = new ContextV2();
        context2.execute(new Strategy() {
            @Override
            public void call() {
                log.info("로직  내부 익명함수로 실행");
            }
        });
    }

    @Test
    void strategyV3(){
        ContextV2 context1 = new ContextV2();
        context1.execute(() -> log.info("비즈니스 로직 1 실행"));
        ContextV2 context2 = new ContextV2();
        context2.execute(() -> log.info("로직 2번을 실행~"));
    }
}