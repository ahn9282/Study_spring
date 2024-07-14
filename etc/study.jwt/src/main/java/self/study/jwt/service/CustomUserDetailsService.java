package self.study.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import self.study.jwt.dto.CustomUserDetails;
import self.study.jwt.dto.UserDto;
import self.study.jwt.mapper.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userData = userMapper.findByUsername(username);
        if (userData != null) {
            return  new CustomUserDetails(userData);
        }

        return null;
    }

}
