package study.REST_API;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import study.REST_API.domain.User;
import study.REST_API.repository.UserRepository;

import java.util.Locale;


@SpringBootApplication
@RequiredArgsConstructor
public class RestApiApplication {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(RestApiApplication.class, args);
		String[] allBeans = ac.getBeanDefinitionNames();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

@PostConstruct
	public User dataInit() {
		User user1 = new User(90001, "User1", "test1", "123");
		User user2 = new User(90002, "User2", "test2", "123");
		User user3 = new User(90003, "User3", "test3", "123");
		userRepository.save(user1);
		userRepository.save(user3);
		userRepository.save(user2);
		return null;
	}

}
