package stduy.designpattern.trace.template;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.slslsl.FakeLogic;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@RestController
public class StudyServiceV1 {

    private final FakeLogic logicProcess;
    private final LogTrace logTrace;

    @GetMapping("/s/v1/{itemId}")
    public String requestV1(@PathVariable("itemId") Integer itemId) {
        AbstractTemplate<Integer> template = new AbstractTemplate<>(logTrace) {
            @Override
            protected Integer call() {
                return logicProcess.process(itemId);
            }
        };
        Object result = template.execute("template Method Pattern");
        String resultStr = "ok : " + result;
        return resultStr;
    }
}
