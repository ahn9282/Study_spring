package study.REST_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(RestApiApplication.class, args);
		String[] allBeans = ac.getBeanDefinitionNames();
		for (String bean : allBeans) {
			System.out.println("bean = " + bean);
		}
	}

}
