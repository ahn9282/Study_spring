package spring.study.proxy.pureproxy.decorator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import spring.study.proxy.pureproxy.decorator.code.*;

import java.util.ConcurrentModificationException;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator(){
        Component realComponent = new RealComponent();

        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();

    }

    @Test
    void decorator1(){
        Component realComponent = new RealComponent();

        Component messageDecorator = new MessageDecorator(realComponent);

        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();

    }
    @Test
    void decorator2(){
        Component realComponent = new RealComponent();

        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        //TimeDecorator 를 한번  거쳐 기능을 추가
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();

    }
}
