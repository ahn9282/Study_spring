package spring.selfstudy.OAuth2.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import spring.selfstudy.OAuth2.dto.SocialUserDTO;

@Mapper
public interface SocialUserMapper {
    @Select("select * from SOCIALUSER where username = #{username}")
    SocialUserDTO findByUserName(String username);

    @Insert("insert into SOCIALUSER (username, email, role)values (#{username},#{email},#{role})")
    void save(SocialUserDTO socialUser);

    @Update("update SOCIALUSER set email = #{email}")
    void updateEmail(SocialUserDTO socialUser);

}
