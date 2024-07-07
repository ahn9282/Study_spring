package study.spring.transaction.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import study.spring.transaction.domain.Log;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepositoryImpl implements LogRepositoryCustom {

    private final EntityManager em;

    @Override
 @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Log saveLogTx(Log data) {

        log.info("로그 저장");
        em.persist(data);
        if (data.getMessage().contains("로그예외")) {
            log.info("로그 저장 시 예외 발생");
            throw new RuntimeException("예외 발생");
        }

        return data;
    }
}
