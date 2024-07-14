package study.querydsl.start;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.start.entity.Hello;
import study.querydsl.start.entity.QHello;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		Hello hello = new Hello();
		hello.setName("hello");
		em.persist(hello);

		JPAQueryFactory query = new JPAQueryFactory(em);
		//QHello qHello = new QHello("h");
		QHello qHello =QHello.hello;
		Hello hello2 = query
				.selectFrom(qHello)
				.fetchOne();
		assertThat(hello2).isEqualTo(hello);
		assertThat(hello2.getId()).isEqualTo(hello.getId());
		assertThat(hello2.getId()).isEqualTo(hello.getId());
	}

}
