package spring.study.self.security;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Slf4j
@Setter
public class UserDetailDto implements UserDetails {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserDetailDto(UserDto userDto){
        this.setId(userDto.getId());
        this.setUsername(userDto.getUsername());
        this.setPassword(userDto.getPassword());
        this.setEnabled(userDto.isEnabled());
        this.setAuthorities(userDto.getAuthorities());

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
        //true : 만료 X
        //false : 만료 O
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠겼는지에 대한 여부
        //true : 잠김 X
        //false : 잠김 O
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료 되었는지에 대한 여부
        //true : 만료 X
        //false : 만료 O
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정이 활성화 되었는지에 대한 여부
        //true : 활성화
        //false : 비활성화
        return enabled;
    }

}
