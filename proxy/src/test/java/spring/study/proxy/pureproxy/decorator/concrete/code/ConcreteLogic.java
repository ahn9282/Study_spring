package spring.study.proxy.pureproxy.decorator.concrete.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteLogic {

    public String operation(){
        log.info("콘트키트 로직 실행");
        return "data";
    }
}
