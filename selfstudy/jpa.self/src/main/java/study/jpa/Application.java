package study.jpa;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.jpa.domain.*;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
	private final EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	Hibernate5JakartaModule hibernate6Module() {
//		return new Hibernate5JakartaModule().enable(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING);
		return new Hibernate5JakartaModule();
	}
}

