package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embeddable//Setter 를 없애 불벽객체로 한다.
public class Address {

    @Column(length = 10)
    private String city;
    @Column(length = 20)
    private String street;
    @Column(length = 5)
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String fullAddress(){
        return getCity() + " " + getStreet() + " " + getZipcode();
    }


    public Address() {
    }
}
