package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//디폴트 생성자 사용 방지
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch=FetchType.LAZY) //다대일 관계
    private Member member;

    @OneToMany(mappedBy = "order",cascade=CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    //묶음  cascade
    //그래서 order객체를 persist 시 여기 collectionList 와 Delivery 객체들을 통해 해당 값들이 존재하는 테이블에도 같이 persist전파
    //persist 뿐 아니라 delete도 전파

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;//jpa에서는 hibernate가 LocalDateTime일 경우 알아서 처리

    private OrderStatus status;//주문 상태 [ORDER, CANCEL]

    // == 연관간계 편의 메서드 == //
    //이는 양방향으로 주입을 한다.
    //인자를 통해 set하면 해당 set한 엔티티에도 .add(this)를 통해 set과 해당 엔티티에 주입도 동시에한다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    //주문 취소
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        orderItems.stream().forEach(e -> e.cancel());
    }

    //==조회 로직 =//
    //전체 주문 가격 조회
    public int getTotalPrice(){
     return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
