package study.jpa.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberForm {

    @NotEmpty
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
