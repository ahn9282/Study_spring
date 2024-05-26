package self.study.jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import self.study.jwt.dto.JoinForm;
import self.study.jwt.dto.UserDto;
import self.study.jwt.mapper.UserMapper;

@Service
@Slf4j
public class JoinService {


    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public JoinService(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public void joinProcess(JoinForm form){

        String username = form.getUsername();
        String password = form.getPassword();

        int isExisted = userMapper.existCheck(username);

        if(isExisted >0){
            return;
        }

        UserDto data = new UserDto();

        data.setUsername(username);
        data.setPassword(passwordEncoder.encode(password));

        userMapper.save(data);
        userMapper.saveAuthority(data);

    }
}
