package spring.studysecurity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import spring.studysecurity.dto.JoinDTO;
import spring.studysecurity.dto.UserDTO;


@Mapper
    public interface UserMapper {

        void save(JoinDTO userDto);

        void saveAuthority(JoinDTO userDto);

    UserDTO findByUsername(String username);
    //쿼림누을 통해 id, username, password, auth를 모두 가지는 UserDTO로 반환
    }


