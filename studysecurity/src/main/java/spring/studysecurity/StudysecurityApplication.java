package spring.studysecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class StudysecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudysecurityApplication.class, args);
	}

}
