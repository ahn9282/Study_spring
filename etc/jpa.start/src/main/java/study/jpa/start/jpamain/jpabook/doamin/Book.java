package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

}
