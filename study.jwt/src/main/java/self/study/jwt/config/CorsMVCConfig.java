package self.study.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Security Config 에서 CORS 설정은 로그인 필터의 경우이고 그 외 다른 cors설정은 여기서 추가적으로 해야하낟.
@Configuration
public class CorsMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");

    }
}
