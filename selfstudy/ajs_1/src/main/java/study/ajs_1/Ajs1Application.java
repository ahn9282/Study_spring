package study.ajs_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import study.ajs_1.controller.ItemValidator;

@SpringBootApplication
public class Ajs1Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Ajs1Application.class, args);
	}

//	@Override
//	public Validator getValidator(){
//		return new ItemValidator();
//	}

}
