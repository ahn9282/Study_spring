package hello.advanced.trace.hellotrace.logtrace;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.FieldTrace;
import org.junit.jupiter.api.Test;

public class FieldLogTraceTest {

    FieldTrace trace = new FieldTrace();

    @Test
    void begin_end_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");

        trace.end(status2);
        trace.end(status1);

    }

    @Test
    void begin_exception_level2(){
        TraceStatus status1 = trace.begin("hello3");
        TraceStatus status2 = trace.begin("hello4");

        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }
}
