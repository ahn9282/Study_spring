package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(//해당 클래스 내 @Conponent 어노테이션이 붙은 클래스를 찾아 자동으로 스프링 빈에 등록해줌

        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
// 지정한 클래스를 제외한 @ComponentScan을 하도록 설정
)
public class AutoAppConfig {


}
