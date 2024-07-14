package spring.studysecurity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JoinDTO {
    //member테이블의 객체
    private long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
