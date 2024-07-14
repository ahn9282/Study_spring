package stduy.designpattern.trace.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {

    private final LogTrace logTrace;

    public T execute(String message) {
        TraceStatus status = null;
        try{
            status = logTrace.begin(message);

            T result = call();

            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
