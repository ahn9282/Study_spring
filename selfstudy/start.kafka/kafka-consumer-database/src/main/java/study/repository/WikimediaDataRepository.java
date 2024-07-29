package study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.WikimediaData;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {

}
