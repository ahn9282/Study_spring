package hello.itemservice;

import hello.itemservice.config.JdbcTemplateV1Config;
import hello.itemservice.config.JdbcTemplateV2Config;
import hello.itemservice.config.JdbcTemplateV3Config;
import hello.itemservice.config.MemoryConfig;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Slf4j
@Import(JdbcTemplateV3Config.class)
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


	/*@Bean
	@Profile("test")
	public DataSource dataSource(){
		log.info("메모리 데이터 베이스 초기화");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=1");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}*/
}