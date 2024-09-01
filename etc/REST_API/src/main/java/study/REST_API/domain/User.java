package study.REST_API.domain;


import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private Integer id;

    @Size(min = 2, message = "Name은 2 글자 이상 입력해 주세요.")
    private String name;

    @Past(message="등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

    public User() {
        this.joinDate = new Date();
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.joinDate = new Date();
    }
}
