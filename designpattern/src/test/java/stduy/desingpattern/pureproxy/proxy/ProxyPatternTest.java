package stduy.desingpattern.pureproxy.proxy;

import org.junit.jupiter.api.Test;
import stduy.desingpattern.pureproxy.proxy.code.CacheProxy;
import stduy.desingpattern.pureproxy.proxy.code.ProxyPatternClient;
import stduy.desingpattern.pureproxy.proxy.code.RealSubject;

public class ProxyPatternTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        CacheProxy cacheProxy = new CacheProxy(new RealSubject());
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
