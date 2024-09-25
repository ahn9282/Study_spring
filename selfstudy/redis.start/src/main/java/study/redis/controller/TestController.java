package study.redis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.redis.domain.TestData;
import study.redis.service.LogService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class TestController {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<List<TestData>> getAll() {
        List<TestData> logs = logService.searchAll();
        return ResponseEntity.ok(logs);
    }

    @DeleteMapping
    public void deleteAll() {
        logService.removeAll();
    }

}
