package study.jpa.start.jpamain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="MBR2")
@SequenceGenerator(name="MEMBER_SEQ_GENERATOR",
sequenceName = "MEMBER_SEQ",      //allocationSize -> 한번 호출 때마다 50개씩을 가져온다. 미리 올려놓는다 보면 된다.
initialValue = 1, allocationSize = 50) // 동시성 이슈를 해결해준다는 장점이 있다.
public class Member2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "MEMBER_SEQ_GENERATOR")//시퀀스 전략의 경우 persist 시 sequence 값을 가지고 온다. 그래서 기본키 값을 미리 알 수 있다.
    private Long id; // 기본키 값을 미리는 알 수 있고, insert 쿼리는 버퍼로 처리된다.

/*   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)//IDENTITY 전략의 경우 insert 시 즉시 쿼리를 실행한다.
    private Long id;//commit 여부를 고려하지 않고 즉시 insert 쿼리문을 실행한다. -> 즉시 영속성 컨텍스트에 등록된다.
    //어떻게 보면 단점이 될 수 있고 장점이 될 수는 있지만, 어떠한 것이든 한 트랜잭션 내 이기에 크게 비약적인 성능향상은 없다.
    //어떻게 보면 id 값을 미리 알 수 있다는 장점이기도 하다.*/

    @Column(name = "name", nullable = false)
    private String username;

}
