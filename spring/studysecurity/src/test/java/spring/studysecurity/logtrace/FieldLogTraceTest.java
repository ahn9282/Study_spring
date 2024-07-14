package spring.studysecurity.logtrace;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.studysecurity.trace.TraceStatus;
import spring.studysecurity.trace.logtrace.LogTrace;

@SpringBootTest
public class FieldLogTraceTest {
    @Autowired
    LogTrace logTrace;

    @Test
    void begin_end_level2(){
        TraceStatus status1 = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.end(status2);
        logTrace.end(status1);

        TraceStatus status_e1 = logTrace.begin("hello-e");
        TraceStatus status_e2 = logTrace.begin("hello-e2");
        logTrace.end(status_e2);
        logTrace.exception(status_e1, new IllegalStateException());
    }
}
