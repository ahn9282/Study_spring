package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Movie extends Item {

    private String director;
    private String actor;


}

