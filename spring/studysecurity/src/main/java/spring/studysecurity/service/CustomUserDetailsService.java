package spring.studysecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.studysecurity.dto.CustomUserDetails;
import spring.studysecurity.dto.UserDTO;
import spring.studysecurity.mapper.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //해당 UserDetailsService를 구현해야 로그인 시 db에서 로그인 검증을 수행
    //로그인 검증은 Spring Security에서는 해당 구현체를 통해서만 가능
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username을 토대로 존재하는 id를 찾음
        //해당 데이터를 UserDTO로 반환함
        UserDTO userData = userMapper.findByUsername(username);
        //존재한다
        if (userData != null) {
            //메서드가 UserDetails로 반환하기에 UserDTO를 주입하여 UserDetails 구현체 반환
            return new CustomUserDetails(userData);
        }
        // db에 존재하는 아이디가 없다.
        return null;
    }
}
