package spring.study.proxy.config.v6_aop.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import spring.study.proxy.trace.TraceStatus;
import spring.study.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.Method;

@Slf4j
@Aspect //어노테이션 기반 프록시 적용
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    //pointcut annotation
    //@Around() 괄호 안에 pointcut 조건 명시
    //@Around 가 가지는 메서드의 로직은 어드바이스가 된다.
    // 그래서 이둘이 합쳐져 어드바이저가 된다.
    //해당 메서드의 반환을 어드바이저로 변환하기에 반환타입이 현재 Object
    @Around("execution(* spring.study.proxy.app..*(..)) && !execution(* spring.study.proxy.app..noLog(..))")
    public Object execute(ProceedingJoinPoint jointPoint) throws Throwable {
                            //ProceedingJoinPoint 이 안에 method 나 class 등에 대한 모든 정보를 가지고 있음
        TraceStatus status = null;
        try{
            //jointPoint 객체를 토대로 message 추출
            String message = jointPoint.getSignature().toShortString();
            status = logTrace.begin(message);
            
            //jointPoint 객체를 토대로 똑같이 proceed(); 로 로직 실행
            Object result = jointPoint.proceed();

            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
