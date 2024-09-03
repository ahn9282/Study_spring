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
import study.REST_API.domain.Post;
import study.REST_API.domain.User;
import study.REST_API.repository.PostRepository;
import study.REST_API.repository.UserRepository;

import java.util.ArrayList;
import java.util.Locale;


@SpringBootApplication
@RequiredArgsConstructor
public class RestApiApplication {

	private final UserRepository userRepository;
	private final PostRepository postRepository;

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

	@Transactional
	@PostConstruct
	public void dataInit() {
		User user1 = new User(null, "User1", "123", "ssn1");
		User user2 = new User(null, "User2", "123", "ssn2");
		User user3 = new User(null, "User3", "123", "ssn3");

		User savedUser1 = userRepository.save(user1);
		User savedUser2 = userRepository.save(user2);
		User savedUser3 = userRepository.save(user3);

		Post post1 = new Post();
		post1.setDescription("반가워용!!! ");
		post1.setUser(savedUser1);

		Post post2 = new Post();
		post2.setDescription("hello ");
		post2.setUser(savedUser1);

		Post post3 = new Post();
		post3.setDescription("WOW!!! ");
		post3.setUser(savedUser2);

		Post post4 = new Post();
		post4.setDescription("nice!!! ");
		post4.setUser(savedUser3);

		postRepository.save(post1);
		postRepository.save(post2);
		postRepository.save(post3);
		postRepository.save(post4);
	}


}
