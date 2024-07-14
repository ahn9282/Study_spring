package study.jwt.more;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.jwt.more.domain.UserEntity;
import study.jwt.more.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner() {


		return args -> {

			UserEntity user1 = new UserEntity();
			user1.setUsername("user1");
			user1.setPassword(passwordEncoder.encode("1234"));
			user1.setRole("ROLE_USER");
			userRepository.save(user1);

			UserEntity user2 = new UserEntity();
			user2.setUsername("user2");
			user2.setPassword(passwordEncoder.encode("1234"));
			user2.setRole("ROLE_USER");
			userRepository.save(user2);
			UserEntity admin = new UserEntity();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("1234"));
			admin.setRole("ROLE_ADMIN");
			userRepository.save(admin);
		};
	}
}
