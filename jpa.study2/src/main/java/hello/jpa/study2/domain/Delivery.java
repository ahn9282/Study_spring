package hello.jpa.study2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //1대1관계
    @JsonIgnore
    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;


    @Embedded
    private Address address;

    //EnumTyoe에 ORDINAL이랑 STRING이 있다. ORDINAL은 숫자로 변환 하기에 향후 확장이나 추가에 있어 문제가 생길 가능성이 높아 STRING을 꼭 사용
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
