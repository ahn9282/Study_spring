package study.REST_API.domain;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonFilter("UserInfo")
public class AdminUser {

    private Integer id;

    @Size(min = 2, message = "Name은 2 글자 이상 입력해 주세요.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

    // @JsonIgnore
    private String password;

    //@JsonIgnore
    private String ssn;

    public AdminUser() {
        this.joinDate = new Date();
    }

    public AdminUser(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.joinDate = new Date();
    }
}
