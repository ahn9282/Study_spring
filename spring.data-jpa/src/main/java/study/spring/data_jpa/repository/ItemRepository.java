package study.spring.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import study.spring.data_jpa.entity.Item;

@EnableJpaRepositories
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


}
