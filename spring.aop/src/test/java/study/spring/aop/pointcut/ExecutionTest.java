package study.spring.aop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import study.spring.aop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {

        helloMethod =   MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        //public java.lang.String study.spring.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod = {}", helloMethod);
    }

    @Test
    void exactMatch() {
        //public java.lang.String study.spring.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String study.spring.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

    }
    @Test
    void allMatch(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch1(){
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void nameMatch2(){
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void nameMatch3(){
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    @Test
    void packageExactMatch1(){
        pointcut.setExpression("execution(* study.spring.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeExactMatch1(){
        pointcut.setExpression("execution(* study.spring.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeExactMatchSuperType(){//부모타입 설정 시 그 부모의 자식 타입도 해당된다. 다형성 유효
        pointcut.setExpression("execution(* study.spring.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatchInternal() throws NoSuchMethodException {//오버라이딩 가능한 메서드인 경우에만 다형성이 유효하다
        pointcut.setExpression("execution(* study.spring.aop.member.MemberService.*(..))");
        //internal의 경우 상속한 메서드가 아닌 MemberServiceImpl의 자체 메서드이다.
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }
    //String 타입의 파라미터 허용
    //(String)
    @Test
    void argsMatch(){
        pointcut.setExpression("execution(* *(String))");//메서드의 파라미터가 String인 조건.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void argsMatchNoArgs(){
        pointcut.setExpression("execution(* *())");//파라미터가 없는 조건
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    //정확히 하나의 파리미터 허용, 모든 타입 허용
    //(Xxx)
    @Test
    void argsMatchStar(){
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    //개수와 무관하게 모든 파리미터 허용, 모든 타입 허용
    //(Xxx)
    @Test
    void argsMatchAll(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(String), (String, Xxx) (String, Xxx, Xxx) (String, ~~~)
    @Test
    void argsMatchComplex(){
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}

