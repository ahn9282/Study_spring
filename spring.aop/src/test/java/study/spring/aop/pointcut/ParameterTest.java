package study.spring.aop.pointcut;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import study.spring.aop.member.MemberService;
import study.spring.aop.member.annotation.ClassAop;
import study.spring.aop.member.annotation.MethodAop;

@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParameterTest.class);
    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("helloA");

    }

    @Aspect
    static class ParameterAspect {

        private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParameterAspect.class);

        @Pointcut("execution(* study.spring.aop.member..*.*(..))")
        private void allMember() {
        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg,..)")//args pointcut을 통해 지정한 인자를 그대로 받아 사용가능
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        //@Before 의 경우 void로 joinPoint.proceed()를 반환하지 않으므로 그대로 덧씌우기만 하는것처럼 가능
        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg) throws Throwable {
            log.info("[logArgs1] arg={}", arg);
        }

        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{}, obj = {}", joinPoint.getSignature(), obj.getClass());
            //실제 대상 구현체를 가리킨다.
        }

        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{}, obj = {}", joinPoint.getSignature(), obj.getClass());
            // 스프링 빈에 등록된 구현체(프록시 객체)를 가리킨다.
        }

        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@annotation]{}, annotation  = {}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@annotation]{}, annotation  = {}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotation value= {}", joinPoint.getSignature(), annotation.value());
        }
    }
}
