package jpabook.jpashop.util;

import lombok.Getter;
import lombok.Setter;

import java.util.SimpleTimeZone;

@Getter
@Setter
public class BookForm {

    private Long id;

    private  String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

}
