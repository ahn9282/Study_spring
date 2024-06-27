package study.jpa.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.jpa.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "D-TYPE")
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void removeStock(int stockQuantity) {
        int resStock = this.stockQuantity - stockQuantity;
        if(resStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resStock;
    }
}
