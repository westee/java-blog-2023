package hello.service;

import hello.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUser(@Param("id") Integer state);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(@Param("username") String state);

    @Insert("INSERT INTO user (username, encrypted_password) VALUES (#{username}, #{encrypted_password}) ")
    void insertUser(@Param("username") String username, @Param("encrypted_password") String password);
}
