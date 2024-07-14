package study.jpa.basic.jpamain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="members")
@ToString
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;
    public Member(){}
    public Member(Long id, String name){
        this.id=id;
        this.name= name;
    }

}
