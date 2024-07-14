package springdata.jpa.self;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springdata.jpa.self.domain.Person;
import springdata.jpa.self.repository.PersonRepository;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringdatajpaSelfThoughtwithmanualApplication {

	private final PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringdatajpaSelfThoughtwithmanualApplication.class, args);
	}
	@Bean
	CommandLineRunner runner() {
		return args -> {

			createMember("Daniel", "Jack", "Daniel");
			createMember("Chris", "Py", "Chris");
			createMember("JK", "Oh", "johu");
			createMember("JSA", "Ahn", "junseob");
			createMember("Huh", "Huh", "set");
			Person saved = repository.findById(1L).orElseThrow(RuntimeException::new);
		};
	}
	 void createMember(String name, String firstname, String lastname){
		Person person = new Person();
		person.setName(name);
		person.setFirstname(firstname);
		person.setLastname(lastname);
		repository.save(person);
	}
}
