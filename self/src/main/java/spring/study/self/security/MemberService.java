package spring.study.self.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {
    @Autowired
    UserMapper userMapper;

    public void insert(UserDto userDto){
        log.info("allive Service");
        userMapper.save(userDto);
        userMapper.saveAuthority(userDto);
    }
}
