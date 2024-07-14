package stduy.desingpattern.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;
import stduy.desingpattern.pureproxy.concreteproxy.code.ConcreteClient;
import stduy.desingpattern.pureproxy.concreteproxy.code.ConcreteLogic;
import stduy.desingpattern.pureproxy.concreteproxy.code.TimeProxy;
import stduy.desingpattern.pureproxy.decorator.code.TimeDecorator;

public class ConcreteProxyTest {

    @Test
    void noProxy(){
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        TimeProxy proxy = new TimeProxy(new ConcreteLogic());
        ConcreteClient client = new ConcreteClient(proxy);
        client.execute();

    }
}
