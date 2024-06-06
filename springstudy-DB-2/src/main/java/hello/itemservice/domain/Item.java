package hello.itemservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity//JPA 객체로 등록시키는 어노테이션
@Data
//@Table(name="item")//이렇게 테이블 명을 명시가 가능하다 그러나 객체 명이랑 같은 경우 생략 가능
public class Item {

    //PK
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //컬럼명과 필드명의 표기법 변환으로 같은 것을 자동으로 매핑해주긴한다. 그래서 아래는 생략이 가능하다.
    @Column(name ="item_name", length=10)//테이블의 컬럼과 매핑
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }//JPA는 public 또는 protected 의 기본 생성자가 필수이기에 꼭 넣어줘야 한다,.

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
