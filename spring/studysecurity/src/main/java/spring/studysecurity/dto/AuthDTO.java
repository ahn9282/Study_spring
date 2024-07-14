package spring.studysecurity.dto;

import lombok.Data;

@Data
public class AuthDTO {
    //권한 테이블용 DTO
    private String username;
    private String auth;

}
