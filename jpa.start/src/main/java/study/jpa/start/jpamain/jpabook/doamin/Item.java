package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
                            //추천되지는 않는 방법
@Entity              //TABLE_PER_CLASS 경우 해당 Item Entity는 추상으로 여겨지고 상속한 Entity 로 테이블만 생성된다.
@Getter                                 //SINGLE_TABLE 로 할 경우 하나의 테이블에 모든 column으로 구성되게 한다.
@Setter                                 //상속한 entity 들을 각자의 테이블로 만들면서 Item pk를 식별자로 둚
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)// JOINED : 슈퍼타입 서브타입 모델로 테이블을 모델링
@DiscriminatorColumn(name = "D_TYPE") //SINGLE_TABLE 경우 안해도 된다.
// 상속하는 Entity가 존재할 시 자동으로 해당 상속 클래스의 이름으로 DTYPE 컬럼이 자동으로 된다.
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;

    private int price;



    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @ManyToMany(mappedBy = "items",cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

}
