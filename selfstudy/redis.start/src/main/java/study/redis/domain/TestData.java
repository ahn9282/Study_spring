package study.redis.domain;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestData {

    @Id
    private Long id;

    String code;

    String name;

}
