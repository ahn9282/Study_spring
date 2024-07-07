package study.spring.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.spring.transaction.domain.Log;

import java.util.Optional;
@Repository
@EnableJpaRepositories
public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

    Optional<Log> findByMessage(String message);
}
