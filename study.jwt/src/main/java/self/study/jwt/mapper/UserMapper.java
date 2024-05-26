package self.study.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;
import self.study.jwt.dto.UserDto;

@Mapper
public interface UserMapper {

    int existCheck(String username);

    void save(UserDto userDto);

    void saveAuthority(UserDto userDto);

    UserDto findByUsername(String username);
}
