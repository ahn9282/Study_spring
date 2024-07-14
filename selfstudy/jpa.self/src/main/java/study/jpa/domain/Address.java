package study.jpa.domain;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }
}
