package study.jpa.basic.jpabook.doamin;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

    private Long memberId;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
