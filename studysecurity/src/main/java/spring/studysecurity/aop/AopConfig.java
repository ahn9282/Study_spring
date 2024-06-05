package spring.studysecurity.aop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import spring.studysecurity.trace.logtrace.LogTrace;
import spring.studysecurity.trace.logtrace.ThreadLocalLogTrace;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect( @Qualifier("trace") LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
    @Bean(name = "trace")
    LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }

}