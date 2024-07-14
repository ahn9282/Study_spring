package stduy.designpattern.trace.proxy.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import stduy.designpattern.trace.TraceStatus;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.app.v1.OrderRepositoryV1;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

    private final OrderRepositoryV1 target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status  = logTrace.begin("OrderRepository.save()");

            target.save(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
