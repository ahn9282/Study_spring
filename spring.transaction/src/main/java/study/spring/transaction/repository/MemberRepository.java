package study.spring.transaction.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import study.spring.transaction.domain.Member;

import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom {


    Optional<Member> findByUsername(String username);
}
