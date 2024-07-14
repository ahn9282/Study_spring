package stduy.designpattern.trace.proxy.v1_proxy.concrete_proxy;

import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.app.v2.OrderControllerV2;
import stduy.designpattern.trace.app.v2.OrderServiceV2;
import stduy.designpattern.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private OrderControllerV2 target;
    private LogTrace trace;

    public OrderControllerConcreteProxy( OrderControllerV2 target, LogTrace trace) {
        super(null);
        this.target = target;
        this.trace = trace;
    }
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
