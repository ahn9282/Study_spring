package spring.study.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import spring.study.proxy.config.AppV1Config;
import spring.study.proxy.config.AppV2Config;
import spring.study.proxy.config.v1_proxy.ConcreteProxyConfig;
import spring.study.proxy.config.v1_proxy.InterfaceProxyConfig;
import spring.study.proxy.trace.logtrace.LogTrace;
import spring.study.proxy.trace.logtrace.ThreadLocalLogTrace;

//@Import(AppV1Config.class)
@Import({ ConcreteProxyConfig.class,InterfaceProxyConfig.class})
@SpringBootApplication(scanBasePackages = "spring.study.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);

	}
		@Bean
	public LogTrace logTrace(){
			return new ThreadLocalLogTrace();
		}
}
