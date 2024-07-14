package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan // 자동 서블릿 스캔 <- 자동등록
@SpringBootApplication
public class ServletApplication {

    public static void main(String[] args) {

		SpringApplication.run(ServletApplication.class, args);
    }

//    @Bean   //이는 스프링의 내장된 뷰 리졸버로 스프링부트는 자동으로 해준다. application.properties의 설정정보를 통해 자동으로 해주나
//    InternalResourceViewResolver internalResourceViewResolver(){     그러나 직접 사용한다면 이렇게 된다.
//        return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
//    }
}
