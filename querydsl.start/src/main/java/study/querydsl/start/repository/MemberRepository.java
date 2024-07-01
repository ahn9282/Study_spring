package study.querydsl.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import study.querydsl.start.entity.Member;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom , QuerydslPredicateExecutor<Member> {

    List<Member> findByUsername(String username);
}
