package spring.studysecurity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    //member테이블과 authority테이블이 분리된 형태이기에 이 둘의 값을 결합하여 전송하는 객체
    private long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String auth;

}
