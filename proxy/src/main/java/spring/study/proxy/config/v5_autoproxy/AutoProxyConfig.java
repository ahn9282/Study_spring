package spring.study.proxy.config.v5_autoproxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.study.proxy.config.AppV1Config;
import spring.study.proxy.config.AppV2Config;
import spring.study.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import spring.study.proxy.config.v4_postprocessor.PackageLogTracePostProcessor;
import spring.study.proxy.trace.logtrace.LogTrace;


@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {
//AutoProxyCreator의 경우 pointcut에만 해당하는 advisor를 추가한 1개의 proxy만을 반환한다.
    // ex ) proxyFactory.addAdvisor(advisor1)
    // ex ) proxyFactory.addAdvisor(advisor2)
    // ex ) proxyFactory.addAdvisor(advisor3)
    // ex ) proxyFactory.addAdvisor(advisor4)
    // ex ) proxyFactory.getProxy();
    // 방식으로 pointcut 조건에 해당하는 다수의 advisor  모두 지닌채로 하나의 proxy 만 반환


     //@Bean
    public Advisor advisor1(LogTrace logTrace) {
        //pontcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    //@Bean
    public Advisor advisor2(LogTrace logTrace){
        //pontcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* spring.study.proxy.app..*(..))");
        //패키지 기준 매칭이라 로그 추적기에서 제외될 no-log도 의도치 않게 포함된다.

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace){
        //pontcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* spring.study.proxy.app..*(..)) && !execution(* spring.study.proxy.app..noLog(..))");
        //패키지 기준 매칭이라 로그 추적기에서 제외될 no-log도 의도치 않게 포함된다.

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
