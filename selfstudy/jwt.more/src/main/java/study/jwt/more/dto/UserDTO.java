package study.jwt.more.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.jwt.more.domain.UserEntity;

@Data
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String role;
    private String name;

    public UserDTO(String username, String role, String name) {
        this.username = username;
        this.role = role;
        this.name = name;
    }
}
