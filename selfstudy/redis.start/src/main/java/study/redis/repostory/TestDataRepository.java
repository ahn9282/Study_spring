package study.redis.repostory;

import org.springframework.data.repository.CrudRepository;
import study.redis.domain.TestData;

public interface TestDataRepository extends CrudRepository<TestData, Long> {
}
