package stduy.desingpattern.strategy;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import stduy.desingpattern.strategy.code.templatecallback.TimeLogTemplate;

@Slf4j
public class TemplateCallbackTest {

    /*
     * 템플릿 콜백 패턴  - 익명 내부 클래스
     * */
    @Test
    void callbackV1(){
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직 1 수행 "));
        template.execute(() -> log.info("비즈니스 로직 2 수행 "));

    }
}
