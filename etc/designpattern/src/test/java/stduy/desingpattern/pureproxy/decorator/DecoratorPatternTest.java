package stduy.desingpattern.pureproxy.decorator;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import stduy.desingpattern.pureproxy.decorator.code.*;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecoratorTest() {
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void decorator1(){
        MessageDecorator decorator = new MessageDecorator(() ->"Message!!");
        DecoratorPatternClient client = new DecoratorPatternClient(decorator);
        client.execute();
        client.execute();
    }
    @Test
    void decorator2(){
        MessageDecorator decorator = new MessageDecorator(() ->"Message!!");
        TimeDecorator decorator1 = new TimeDecorator(decorator);
        DecoratorPatternClient client = new DecoratorPatternClient(decorator1);
        client.execute();
    }
}
