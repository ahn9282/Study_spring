package stduy.designpattern.trace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stduy.designpattern.trace.app.v1.*;

@Configuration
public class AppV1Config {

    @Bean
    public OrderControllerV1 orderControllerV1(){
        return new OrderControllerV1Impl(orderServiceV1());
    }
    @Bean
    public OrderServiceV1 orderServiceV1(){
        return new OrderServiceV1Impl(orderRepositoryV1());
    }
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(){
        return new OrderRepositoryV1Impl();
    }

}
