package study.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager boardCacheManager(RedisConnectionFactory redisConnectionFactory){

        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith( /// Rdis에 Key를 저장할 때 String 직렬화 저장
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                        )
                .serializeValuesWith(// Redis에 Value를 저장할 때 Json으로 직렬화 저장
                        RedisSerializationContext.SerializationPair.fromSerializer( new Jackson2JsonRedisSerializer<Object>(Object.class) )
                )
                .entryTtl(Duration.ofMinutes(1L));// 데이터의 만료기간(TTL) 설정

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }
}
