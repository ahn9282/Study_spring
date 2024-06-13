package study.jpa.start.jpamain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MBR")
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME", 
            unique = false,  //unique  그러나 유니크 설정의 경우 제약 조건의 이름을 랜덤으로 주기 떄문에 잘 사용안함
            length = 20,    // DDL 영향 도메인 및 컬럼 명 지정
            updatable = false, //변경 불가능
    columnDefinition = "varchar(100) default 'empty'") //ddl 문 설정 직접 가능
    private String name;

    private BigDecimal age;

   //ENUM 타입 설정 ORDINAL이 디폴트인데, 이 경우 숫자 순으로 자동 저장된다.
    //그렇기에 문제가 생길 가능성이 있고 STRING 의 경우 값 그대로 문자열로 저장해서 이것이 권장된다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;

    @Lob
    private String description;


}
