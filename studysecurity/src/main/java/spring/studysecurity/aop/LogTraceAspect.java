package spring.studysecurity.aop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import spring.studysecurity.trace.TraceStatus;
import spring.studysecurity.trace.logtrace.LogTrace;

@Slf4j
@Aspect //어노테이션 기반 프록시 적용
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Around("execution(* spring.studysecurity.controller..*(..)) || " +
            "execution(* spring.studysecurity.service..*(..)) || " +
            "execution(* spring.studysecurity.mapper..*(..))")
    public Object execute(ProceedingJoinPoint jointPoint) throws Throwable {
        TraceStatus status = null;
        try{
            String message = jointPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = jointPoint.proceed();

            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}