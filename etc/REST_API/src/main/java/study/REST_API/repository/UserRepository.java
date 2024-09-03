package study.REST_API.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import study.REST_API.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
