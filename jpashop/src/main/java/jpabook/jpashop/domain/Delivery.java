package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //1대1관계
    @OneToOne(mappedBy = "delivery")
    private Order order;


    @Embedded
    private Address address;

    //EnumTyoe에 ORDINAL이랑 STRING이 있다. ORDINAL은 숫자로 변환 하기에 향후 확장이나 추가에 있어 문제가 생길 가능성이 높아 STRING을 꼭 사용
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
