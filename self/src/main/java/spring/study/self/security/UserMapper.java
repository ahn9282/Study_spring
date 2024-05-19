package spring.study.self.security;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

     void save(UserDto userDto);
    void saveAuthority(UserDto userDto);

    @Select("SELECT username, password, enabled FROM member WHERE username = #{username}")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "authorities", column = "username",
                    many = @Many(select = "getAuthorities"))
    })
    UserDto loadByUsername(String username);

    @Select("SELECT auth FROM authority WHERE username = #{username}")
    List<AuthDto> getAuthorities(String username);
}
