package spring.studysecurity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.studysecurity.dto.JoinDTO;
import spring.studysecurity.mapper.UserMapper;

@Service
@Slf4j
public class JoinService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        log.info("join Process..");
        //비밀번호를 암호화 후 저장
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        userMapper.save(joinDTO);
        userMapper.saveAuthority(joinDTO);
    }
}
