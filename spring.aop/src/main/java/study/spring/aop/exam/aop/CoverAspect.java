package study.spring.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@Aspect
public class CoverAspect {


    @Around("@annotation(study.spring.aop.exam.annotation.Trace)")
    public Object transactionCover(ProceedingJoinPoint joinPoint)throws Throwable{
      try{

          log.info("트랜잭션 커버 중");

          return joinPoint.proceed();


      }catch(Exception e){

          log.info("트랜잭션 중 예외 발생");
          throw new RuntimeException(e);
      }
    }


//    @Pointcut("")
//    public void isTransaction(){
//
//    }
}
