package study.jpql;

import jakarta.persistence.*;

@Entity
@Table(name = "product_jpql")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int stockAmount;
}
