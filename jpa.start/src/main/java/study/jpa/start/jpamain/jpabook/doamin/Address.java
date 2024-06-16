package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embeddable//Setter 를 없애 불벽객체로 한다.
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }



    public Address() {
    }
}
