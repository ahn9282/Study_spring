package spring.study.proxy.pureproxy.decorator.concrete;

import org.junit.jupiter.api.Test;
import spring.study.proxy.pureproxy.decorator.concrete.code.ConcreteClient;
import spring.study.proxy.pureproxy.decorator.concrete.code.ConcreteLogic;
import spring.study.proxy.pureproxy.decorator.concrete.code.TimeProxy;

public class ConcreteProxyTest {


    @Test
    void noProxy(){
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(logic);
        client.execute();

    }

    @Test
    void addProxy(){
        ConcreteLogic logic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(logic);
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
