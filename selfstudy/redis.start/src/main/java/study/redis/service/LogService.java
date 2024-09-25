package study.redis.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.redis.domain.TestData;
import study.redis.repostory.TestDataRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    private final TestDataRepository testDataRepository;

    public LogService(TestDataRepository testDataRepository) {
        this.testDataRepository = testDataRepository;
    }

    @Cacheable(cacheNames = "searchAll", key = "#root.target + #root.methodName", sync = true, cacheManager = "rcm")
    public List<TestData> searchAll() {
        List<TestData> list = new ArrayList<>();
        testDataRepository.findAll().forEach(list::add);
        return list;
    }

    @CacheEvict(cacheNames = "searchAll", allEntries = true, beforeInvocation = true, cacheManager = "rcm")
    @Transactional
    public void removeAll() {
        testDataRepository.deleteAll();

    }
}
