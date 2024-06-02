package spring.study.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.study.proxy.app.v2.OrderControllerV2;
import spring.study.proxy.app.v2.OrderRepositoryV2;
import spring.study.proxy.app.v2.OrderServiceV2;
import spring.study.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import spring.study.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import spring.study.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import spring.study.proxy.trace.logtrace.LogTrace;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    OrderControllerV2 orderControllerV2(LogTrace logTrace) {

        OrderControllerV2 orderControllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(orderControllerImpl, logTrace);
    }

    @Bean
    OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 orderServiceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(logTrace, orderServiceImpl);
    }
    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 repositoryImpl = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(repositoryImpl, logTrace);
    }
}
