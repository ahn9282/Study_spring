package spring.study.proxy.pureproxy;

import org.junit.jupiter.api.Test;
import spring.study.proxy.code.CacheProxy;
import spring.study.proxy.code.ProxyPatternClient;
import spring.study.proxy.code.RealSubject;

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
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();

    }
}
