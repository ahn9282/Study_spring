package spring.study.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import spring.study.proxy.app.v1.OrderRepositoryV1;
import spring.study.proxy.trace.TraceStatus;
import spring.study.proxy.trace.logtrace.LogTrace;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

    private final OrderRepositoryV1 target;
    private final LogTrace logTrace;
    
    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try{
         status = logTrace.begin("OrderRepository.request()");  
         //target 호출
            target.save(itemId);
            logTrace.end(status);

        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
