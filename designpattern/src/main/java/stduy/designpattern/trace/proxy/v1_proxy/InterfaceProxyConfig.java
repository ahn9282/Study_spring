package stduy.designpattern.trace.proxy.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.app.v1.*;
import stduy.designpattern.trace.proxy.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import stduy.designpattern.trace.proxy.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import stduy.designpattern.trace.proxy.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace){
        return new OrderControllerInterfaceProxy(new OrderControllerV1Impl(orderService(logTrace)), logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){
        return new OrderServiceInterfaceProxy(new OrderServiceV1Impl(orderRepository(logTrace)),logTrace);
    }
    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace){
        return new OrderRepositoryInterfaceProxy(new OrderRepositoryV1Impl(),logTrace);
    }
}
