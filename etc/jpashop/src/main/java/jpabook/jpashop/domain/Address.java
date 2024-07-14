package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) { 
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    //protected 로 접귽지사자를 명시할 경우 JPA 에선 안전하다.
    protected Address(){
        
    }
}
