package study.spring.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import study.spring.transaction.domain.Order;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {


}
