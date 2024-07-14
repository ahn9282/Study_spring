package stduy.designpattern.trace.callback;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import stduy.designpattern.trace.LogicProcess;
import stduy.designpattern.trace.logtrace.LogTrace;

@Repository
@Data
@Slf4j
public class FakeLogicV2 implements LogicProcess {

    private final TraceTemplate template;

    public FakeLogicV2(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }


    public Integer process(int a) {
        log.info("로직 수행 중~~  "+a+" 받음 1 더하는 중");
        return a + 1;
    }


    @Override
    public Integer save(Integer itemId) {
        return template.execute("repository", () -> {
            log.info("레포지토리 값 {}", itemId);
            return 5;
        });
    }
}
