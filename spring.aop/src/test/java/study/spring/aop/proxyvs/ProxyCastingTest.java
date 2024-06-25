package study.spring.aop.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import study.spring.aop.member.MemberService;
import study.spring.aop.member.MemberServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProxyCastingTest {
    
    @Test
    void jdkProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);//이를 false 할 경우 default인 CGLIB 가아니라 JDK 동적프록시 사용

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        assertThrows(ClassCastException.class,() -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        } );
        //안된다. JDK 동적프록시에 경우 같은 또 다른 구현체의 방식으로 만들어내기 떄문이다.
        //MemberServiceImpl의 경우 MemberService의 새로운 구현체 프록시객체를 만드는 구조이기 때문이다.
    }

    @Test
    void cglibProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);//이를 true CGLIB 프록시 객체로 설정 (default)

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        //CGLIB 프록시를 구현 클래스로 캐스팅 성공
        assertThrows(ClassCastException.class,() -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        } );
    }
}
