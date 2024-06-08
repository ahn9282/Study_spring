package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne //다대일 관계
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;//jpa에서는 hibernate가 LocalDateTime일 경우 알아서 처리

    private OrderStatus status;//주문 상태 [ORDER, CANCELs]
}
