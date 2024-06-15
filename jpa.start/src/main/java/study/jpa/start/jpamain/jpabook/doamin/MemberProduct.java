package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class MemberProduct {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private User member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;
    private LocalDateTime orderDate;

}
