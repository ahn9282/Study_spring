package hello.jpa.study2.repository;

import hello.jpa.study2.domain.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}