package study.spring.aop.internalcall;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    // private ApplicationContext applicationContext; applicationContext의 경우 너무 거대하다.
    private final ObjectProvider<CallServiceV2> callServiceProvider;
    // 빈 객체 꺼내기용
    
    public CallServiceV2(ApplicationContext applicationContext, ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;

    }

    public void external() {
        log.info("call external");
        //CallServiceV2 callServiceV2 = callServiceProvider.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); //빈에서 가죠와서 메서드 호출

    }
    public void internal() {
        log.info("call internal");
    }
}
