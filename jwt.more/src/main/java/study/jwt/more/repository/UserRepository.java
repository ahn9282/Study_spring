package study.jwt.more.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import study.jwt.more.domain.UserEntity;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
