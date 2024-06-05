package spring.studysecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import spring.studysecurity.aop.AopConfig;
import spring.studysecurity.trace.logtrace.LogTrace;
import spring.studysecurity.trace.logtrace.ThreadLocalLogTrace;

@SpringBootApplication
@MapperScan
@Import(AopConfig.class)
public class StudysecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudysecurityApplication.class, args);
	}




}
