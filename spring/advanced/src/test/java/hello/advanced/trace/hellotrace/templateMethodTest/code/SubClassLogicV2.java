package hello.advanced.trace.hellotrace.templateMethodTest.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubClassLogicV2 extends AbstractTemplate {
    @Override
    protected void call() {
        log.info("비즈니스 로직 2 실행");

    }
}
