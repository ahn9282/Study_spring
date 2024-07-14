package stduy.designpattern.trace.proxy.v1_proxy.concrete_proxy;

import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.app.v2.OrderRepositoryV2;
import stduy.designpattern.trace.app.v2.OrderServiceV2;
import stduy.designpattern.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final LogTrace trace;
    private final OrderServiceV2 target;

    public OrderServiceConcreteProxy( LogTrace trace, OrderServiceV2 target) {
        super(null);
        this.trace = trace;
        this.target = target;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status  = trace.begin("OrderService.orderItem()");

            target.orderItem(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }    }
}
