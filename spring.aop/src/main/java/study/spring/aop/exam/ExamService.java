package study.spring.aop.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.spring.aop.exam.annotation.Trace;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository repository;

    @Trace
    public void request(String itemId) {
        repository.save(itemId);

    }
}
