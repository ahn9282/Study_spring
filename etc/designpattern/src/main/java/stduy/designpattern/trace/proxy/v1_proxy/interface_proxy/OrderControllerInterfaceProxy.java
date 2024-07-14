package stduy.designpattern.trace.proxy.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.app.v1.OrderControllerV1;

@RequiredArgsConstructor
@RestController
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 target;
    private final LogTrace trace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status  = trace.begin("OrderController.request()");

            String result = target.request(itemId);
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
