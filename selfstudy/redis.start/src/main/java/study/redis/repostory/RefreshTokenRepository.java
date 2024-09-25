package study.redis.repostory;

import org.springframework.data.repository.CrudRepository;
import study.redis.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByAuthId(String authId);

}
