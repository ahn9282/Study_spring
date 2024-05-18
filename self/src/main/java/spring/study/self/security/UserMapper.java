package spring.study.self.security;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

     void save(UserDto userDto);
    void saveAuthority(UserDto userDto);
    UserDto findByUsername(String username);
}
