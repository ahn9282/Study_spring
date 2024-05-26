package self.study.jwt.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
//해당 JWT 구현 방식은 0.12.3으로 최신 번전이나 많이 쓰는 0.11.5랑은 구현방식이 많이 다르다.
//해당 방식은 0.12.3의 구현 방식이므로 이를 주의해야 한다.
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        //key를 부러와 이를 기반으로 객체 키를 생성
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username",String.class);
    }
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, long expiredMs) {
        return Jwts.builder()
                .claim("username", username)//특정 key에 대한 데이터 넣기
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey)//암호화
                .compact();//토큰 컴팩 -> 반환
    }
}
