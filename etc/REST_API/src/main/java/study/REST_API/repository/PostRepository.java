package study.REST_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.REST_API.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
