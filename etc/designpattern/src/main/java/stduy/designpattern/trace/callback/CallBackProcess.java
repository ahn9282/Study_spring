package stduy.designpattern.trace.callback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.LogicProcess;


@RestController
public class CallBackProcess {

    private final TraceTemplate template;
    private final LogicProcess logicProcess;

    public CallBackProcess(LogicProcess logicProcess, LogTrace logTrace) {
        this.template = new TraceTemplate(logTrace);
        this.logicProcess = logicProcess;
    }


    @GetMapping("/callback/v2/{itemId}")
    public String requestV1(@PathVariable("itemId") Integer itemId) {
        Integer resultV = template.execute("message", () -> logicProcess.save(itemId));

         return "ok : " + resultV;
    }

}
