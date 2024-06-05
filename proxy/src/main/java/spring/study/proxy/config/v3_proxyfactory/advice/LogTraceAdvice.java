package spring.study.proxy.config.v3_proxyfactory.advice;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import spring.study.proxy.trace.TraceStatus;
import spring.study.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class LogTraceAdvice implements MethodInterceptor {

    private final LogTrace logTrace;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TraceStatus status = null;
        try{
            //invocation 으로 메서드를 추출해 target 으로 사용 가능
            Method target = invocation.getMethod();

            String message = target.getDeclaringClass().getSimpleName() + "." + target.getName() + "()";
            //클래스 이름                    .           메서드 이름      ()
            status = logTrace.begin(message);
            //로직 호출

            Object result = invocation.proceed();

            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }    }
}
