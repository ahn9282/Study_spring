package study.jpa.basic.jpabook.doamin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name="ORDER_ITEM_ID")
    private Long id;

    @Column(name="ORDER_ID")
    private Long orderId;
    @Column(name = "ITEM_ID")
    private Long itemId;

    private int orderPrice;
    private int count;

}
