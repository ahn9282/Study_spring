package study.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect//포인트 컷 분리
public class AspectV2 {

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature());// join point 시그니처
        return joinPoint.proceed();
    }
    @Pointcut("execution(* study.spring.aop.order..*(..))")
    private void allOrder(){}
}
