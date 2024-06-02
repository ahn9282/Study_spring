package spring.study.proxy.config.v1_proxy.concrete_proxy;

import lombok.RequiredArgsConstructor;
import spring.study.proxy.app.v2.OrderRepositoryV2;
import spring.study.proxy.app.v2.OrderServiceV2;
import spring.study.proxy.trace.TraceStatus;
import spring.study.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;


    public OrderServiceConcreteProxy( LogTrace logTrace, OrderServiceV2 target) {
        super(null);
        this.logTrace = logTrace;
        this.target = target;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try{
            status = logTrace.begin("OrderService.orderItem()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);

        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
