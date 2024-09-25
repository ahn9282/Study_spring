package study.redis.domain;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "refresh_token") /// 설장한 값을  Redis의 key 값 prefix로 사용
public class RefreshToken {

    @Id
    private String authId;

    @Indexed
    private String token;

    private String role;

    @TimeToLive
    private long ttl;

    public RefreshToken update(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
        return this;
    }
}
