package spring.study.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //해당 인자 MethodInvocation invocation 에 실행 될 메서드에 대한 정보가 이미 들어있다.
        // 그 이유는 프록시 팩토리로 프록시를 생성하는 단계에서 target정보를 파라미터로 전달받기 떄문
        log.info("TimeAdvice 실행");
        long startTime = System.currentTimeMillis();

       // Object result = method.invoke(target, args);
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeAdvice 종료 resultTime = {}", resultTime);

        return result;
    }

}
