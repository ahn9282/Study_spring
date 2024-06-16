package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@Setter
@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //CascadeType.ALL 일 경우 해당 parent Entity 여부에 따라 같이 적용된다.
    @OneToMany(mappedBy = "parent",fetch = LAZY)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }
}
