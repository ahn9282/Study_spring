package study.spring.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {


    @Around("study.spring.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (RuntimeException e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[리소스 릴리즈 ]", joinPoint.getSignature());
        }

    }

    @Before("study.spring.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "study.spring.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void deReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {}, return = {}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "study.spring.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void deReturn2(JoinPoint joinPoint, String result) {
        log.info("[return2] {}, return = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "study.spring.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info(" [ex] {} message = {}", ex);
    }

    @After(value = "study.spring.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}