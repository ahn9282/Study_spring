package spring.study.proxy.adviser;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import spring.study.proxy.common.advice.TimeAdvice;
import spring.study.proxy.common.service.ServiceImpl;
import spring.study.proxy.common.service.ServiceInterface;

@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("여러 프록시")
    void multiAdvisorTest1(){
        //client -> proxy2(advisor2) -> proxy1(advisor1) -> target
        //프록시 1 생성
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE,new Advice1() );
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory.getProxy();


        //프록시2 생성
        //연이어서 프록시 2로 하는 것이 이기에 target이 아니라 proxy1이 들어간다.
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);
        ServiceInterface proxy2= (ServiceInterface)proxyFactory2.getProxy();

        proxy2.save();
        proxy2.find();

    }


    @Test
    @DisplayName("하나의 프록시 여러개의 어드바이저")
    void multiAdvisorTest2(){
        //client -> proxy ->advisor2 -> advisor1 -> target

        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE,new Advice1() );
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        //프록시 생성
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        //실행
        proxy.save();
        proxy.find();
    }

    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            log.info("advice1 호출");
            return invocation.proceed();
        }

    }
    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            log.info("advice2 호출");
            return invocation.proceed();
        }

    }
}
