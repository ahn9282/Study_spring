package spring.study.self.security;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Slf4j
@Setter
public class TestUserDetailDto implements UserDetails {
    private String username;
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public TestUserDetailDto(UserDto userDto){
        this.setUsername(userDto.getUsername());
        this.setPassword(userDto.getPassword());

    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정이 만료되었는지에 대한 여부
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠겼는지에 대한 여부
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료 되었는지에 대한 여부
        return false;
    }

    @Override
    public boolean isEnabled() {
        //계정이 활성화 되었는지에 대한 여부
        return false;
    }
}
