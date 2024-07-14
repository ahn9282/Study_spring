package stduy.designpattern.trace.proxy.v1_proxy;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stduy.designpattern.trace.app.v2.*;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.proxy.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import stduy.designpattern.trace.proxy.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import stduy.designpattern.trace.proxy.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import stduy.designpattern.trace.proxy.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import stduy.designpattern.trace.proxy.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import stduy.designpattern.trace.proxy.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderControllerV2 orderController(LogTrace logTrace){
        return new OrderControllerConcreteProxy(new OrderControllerV2(orderServiceV2(logTrace)), logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace){
        return new OrderServiceConcreteProxy(logTrace, new OrderServiceV2(orderRepositoryV2(logTrace)));
    }
    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace){
        return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(),logTrace);
    }
}
