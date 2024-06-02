package spring.study.proxy.pureproxy.decorator.concrete.code;

import lombok.extern.slf4j.Slf4j;
//구현이 아닌 상속과 주입 새성을 통해 데코레이터 기능
@Slf4j
public class TimeProxy extends ConcreteLogic {

    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("Time Proxy 실행");
        long startTime = System.currentTimeMillis();
        String result = concreteLogic.operation();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Time Proxy 종료 resultTiem = {}ms",resultTime );
        return result;
    }
}
