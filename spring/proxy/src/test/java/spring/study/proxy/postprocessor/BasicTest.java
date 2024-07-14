package spring.study.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BasicTest {

    @Test
    void basicConfig(){
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(BasicConfig.class);
        //생성자 파라미터에 컨테이너에 등록한 클래스를 명시

        //A는 빈으로 등록 됨
        A a = applicationContext.getBean(A.class);

        a.helloA();

        //B는 빈 등록 아님
       // B b = applicationContext.getBean(B.class);
        //해당 코드 실행 시 applicationContext에는 B class의 bean이 등록되어 있지 않아 NoSuchBeanDefinitionException 발생

        //예외 발생 여부를 확인하는 테스트
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class));
        //assertJ가 아닌 JUnit 패키지에서 제공하는 Assertions 사용 -> 문법(메서드)이 다름
        //NoSuchBeanDefinitionException 이 해당 람다를 통한 테스트에서 해당 예외가 터져야 테스트 성공
    }

    @Configuration
    static class BasicConfig{
        @Bean(name = "beanA")
        public A a(){
            return new A();
        }
    }

    static class A{
        public void helloA(){
            log.info("hello A");
        }
    }
    static class B{
        public void helloB(){
            log.info("hello B");
        }
    }
}
