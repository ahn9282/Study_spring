package spring.study.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import spring.study.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import spring.study.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import spring.study.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import spring.study.proxy.config.v5_autoproxy.AutoProxyConfig;
import spring.study.proxy.config.v6_aop.AopConfig;
import spring.study.proxy.trace.logtrace.LogTrace;
import spring.study.proxy.trace.logtrace.ThreadLocalLogTrace;

//@Import(AppV1Config.class)
//@Import( {ProxyFactoryConfigV1.class, ProxyFactoryConfigV2.class})
//@Import(BeanPostProcessorConfig.class)
//@Import(AutoProxyConfig.class)
@Import(AopConfig.class)
@SpringBootApplication(scanBasePackages = "spring.study.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);

	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
