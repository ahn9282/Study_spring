package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

   @OneToOne(mappedBy = "locker",cascade = CascadeType.ALL)
   private User member;


}
