package spring.study.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.study.proxy.app.v1.*;
import spring.study.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import spring.study.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import spring.study.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import spring.study.proxy.trace.logtrace.LogTrace;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
      return   new OrderControllerInterfaceProxy(controllerImpl,logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {

        OrderServiceV1Impl orderServiceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);

    }

}
