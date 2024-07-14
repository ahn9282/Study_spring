package spring.study.self.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> authorities;
}
