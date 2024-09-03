package study.REST_API.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value={"password", "ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title="사용자 ID", description = "사용자 ID는 자동 생성 됩니다.")
    private Integer id;

    @Schema(title="사용자 이름", description = "사용자 이름을 입력합니다.")
    @Size(min = 2, message = "Name은 2 글자 이상 입력해 주세요.")
    private String name;

    @Schema(title="사용자 등록날짜", description = "사용자 등록일, 입력하징 낳으면 현재 날짜가 지정됩니다.")
    @Past(message="등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

   // @JsonIgnore
   @Schema(title="사용자 비밀번호", description = "사용자 비밀번호를 입력합니다.")
    private String password;

    //@JsonIgnore
    @Schema(title="사용자 주민번호", description = "사용자 주민번호를 입력합니다.")
    private String ssn;


    public User() {
        this.joinDate = new Date();
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.joinDate = new Date();
    }

    public User(Integer id,String name, String password, String ssn) {
        this.ssn = ssn;
        this.password = password;
        this.name = name;
        this.id = id;
        this.joinDate = new Date();
    }
}
