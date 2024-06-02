package spring.study.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import spring.study.proxy.cglib.code.TimeMethodInterceptor;
import spring.study.proxy.common.service.ConcreteService;

@Slf4j
public class CglibTest {

    @Test
    void cglibTest(){
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        //파라미터로 받은 클래스를 상위 클래스로 cglib 클래스를 만든다.
        enhancer.setCallback(new TimeMethodInterceptor(target));
        //로직의 메서드를 callback 설정

        //설정을 마친 뒤 proxy 생성 설정 클래스 타입의 프록시 생성
        ConcreteService proxy = (ConcreteService) enhancer.create();
        //Class 체크
        log.info("target Class ={}", target.getClass());
        log.info("proxy Class ={}", proxy.getClass());

        proxy.call();//target 로직 실행
    }
}
