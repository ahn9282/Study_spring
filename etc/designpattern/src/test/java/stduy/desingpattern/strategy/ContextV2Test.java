package stduy.desingpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import stduy.desingpattern.strategy.code.strategy.*;

@Slf4j
public class ContextV2Test {

    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행");
            }
        });
        ContextV2 context2 = new ContextV2();
        context2.execute(() -> log.info("비즈니스 로직 2 실행"));

    }

}
