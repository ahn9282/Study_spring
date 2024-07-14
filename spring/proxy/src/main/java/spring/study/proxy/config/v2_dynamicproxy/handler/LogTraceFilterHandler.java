package spring.study.proxy.config.v2_dynamicproxy.handler;

import org.springframework.util.PatternMatchUtils;
import spring.study.proxy.trace.TraceStatus;
import spring.study.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {
    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //메서드 이름 필터
        String methodName = method.getName();
        //save, request, request*, *est
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            //로그 추적기에 예외를 둘 메서드들을 patterns에 넣고 match시켜 아닐 경우
            // 해당 메서드의 로직만 실행하도록 하고 종료하도록 핸들러 자체를 구현
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        try{
            //Method 객체를 토대로 실행하는 메서드 명 변수 생성
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            //클래스 이름                    .           메서드 이름      ()
            status = logTrace.begin(message);
            //로직 호출
            Object result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
