package hello.jpa.study2.domain;

import hello.jpa.study2.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderItem")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//디폴트 생성자 사용 방지
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //==비즈니스 로직 ==//
    public void cancel(){
        getItem().addStock(count);
    }

    //==생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }

    //==조회 로직=//
    //주문 상품 전체 가격 조회
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
