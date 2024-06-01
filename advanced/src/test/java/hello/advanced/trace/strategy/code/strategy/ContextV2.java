package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 필드에 전략을 보관하는 방식
 */
@Slf4j
public class ContextV2 {

    //파라미터로 전달 받는 방식
    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
        strategy.call();

        long endTime = System.currentTimeMillis();
        long result = endTime - startTime;
        log.info("result Time = {}", result);
    }
}
