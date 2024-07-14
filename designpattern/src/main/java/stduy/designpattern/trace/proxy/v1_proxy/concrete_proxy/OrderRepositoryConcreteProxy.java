package stduy.designpattern.trace.proxy.v1_proxy.concrete_proxy;

import lombok.RequiredArgsConstructor;
import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.app.v2.OrderRepositoryV2;
import stduy.designpattern.trace.logtrace.LogTrace;

public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

    private final OrderRepositoryV2 target;
    private final LogTrace trace;

    public OrderRepositoryConcreteProxy(OrderRepositoryV2 target, LogTrace trace) {
        this.target = target;
        this.trace = trace;
    }

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status  = trace.begin("OrderRepository.save()");

            target.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
