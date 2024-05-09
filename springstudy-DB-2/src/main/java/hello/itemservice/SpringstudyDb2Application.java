package hello.itemservice;

import hello.itemservice.config.JdbcTemplateV1Config;
import hello.itemservice.config.MemoryConfig;
import hello.itemservice.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

//@Import(MemoryConfig.class)
@Import(JdbcTemplateV1Config.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class SpringstudyDb2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringstudyDb2Application.class, args);
	}

	@Bean
	@Profile("local") //빈 등록에 대한 조건 괄호에 해당하는 프로필로  해당해야만 함
	//프로필은 주로 로컬과 회사 개발 환경에 분리를 하기 위하여 사용한다.
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

}