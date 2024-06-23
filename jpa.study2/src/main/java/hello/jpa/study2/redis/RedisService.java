package hello.jpa.study2.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveVisitorCount(String visitorId) {

        redisTemplate.opsForValue().increment(visitorId);
    }

    public Long getVisitorCount(String visitorId) {
        Object count = redisTemplate.opsForValue().get(visitorId);
        return count != null ?(Long) count : 0L;
    }
}
