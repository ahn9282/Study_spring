package study.jpa.basic.jpabook.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "MEMBER")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String zipcode;

}
