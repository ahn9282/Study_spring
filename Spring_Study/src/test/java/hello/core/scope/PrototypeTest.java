package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("findPrototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("findPrototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
        prototypeBean1.destroy();//이렇게 일일히 명시하지 않으면 @PreDestroy가 전혀 실행되지 않는다.
        prototypeBean2.destroy();

        ac.close();// 프로토타입 스코프 빈은 스프링 컨테이너에서 조횔 할 떄 생성된다.
        //프로토 타입 빈은 생성하고 반환하며 사라진다. 따라서 프로토타입빈은 @PreDestroy가 전혀 실행되지 않는다.
    }
    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("singletonBean.destroy");
        }
    }
}
