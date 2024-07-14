package stduy.designpattern.trace.callback;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
public class TraceTemplate {

    private final LogTrace trace;

    public <T> T execute(String message, TraceCallback<T> callback) {
            TraceStatus status = null;
            try{
                status = trace.begin(message);

                T result = callback.call();

                trace.end(status);
                return result;
            }catch(Exception e){
                trace.exception(status, e);
                throw e;
            }
    }
}
