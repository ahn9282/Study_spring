package study.spring.transaction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.spring.transaction.domain.Log;
import study.spring.transaction.repository.LogRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {
    private final LogRepository logRepository;

    @Transactional
    public void saveLog(Log logMessage) {
        log.info("log 저장");
        logRepository.save(logMessage);

        if (logMessage.getMessage().contains("로그예외")) {
            log.info("log 저장 시 예외 방생");
            throw new RuntimeException("예외 발생");
        }

    }
}
