package stduy.designpattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import stduy.designpattern.trace.logtrace.LogTrace;
import stduy.designpattern.trace.logtrace.LogTracer;
import stduy.designpattern.trace.proxy.dynamic_proxy.jdkdynamic.DynamicProxyBasicConfig;
import stduy.designpattern.trace.proxy.v1_proxy.ConcreteProxyConfig;
import stduy.designpattern.trace.proxy.v1_proxy.InterfaceProxyConfig;

//@Import({AppV1Config.class, AppV2Config.class})
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
@Import(DynamicProxyBasicConfig.class)
@SpringBootApplication(scanBasePackages = "study.designpattern")
public class DesignPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesignPatternApplication.class, args);
	}

	@Bean
	public static LogTrace logTrace(){
		return new LogTracer();
	}
}
