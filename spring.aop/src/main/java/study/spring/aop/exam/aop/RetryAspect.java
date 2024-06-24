package study.spring.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import study.spring.aop.exam.annotation.Retry;

@Slf4j
@Aspect
public class RetryAspect {

    //해당 어노테이션의 타입을 인자로 받으면 import 등 정보를 따와 pointcut에도 적용시켜 가능하다.
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("[retry] {} retry = {}", joinPoint.getSignature(), retry);
        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] ry count = {}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
