package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

public abstract class AbstractTemplate<T> {
    //파라미터에 제네릭 도입 방법

    private final LogTrace trace;

    protected AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }


    //    <T>(parameter)에 String 타입이므로 String 타입을 반환한다.
    public T execute(String message) {
        TraceStatus status = null;
        try{
            status = trace.begin(message);
            //로직 호출
            T result = call();//로직 후 반환 값

            trace.end(status);
            return result;
        }catch(Exception e){
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
