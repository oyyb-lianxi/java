package com.example.mapper;


import com.example.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> selectAll();

    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User getById(@Param("openid") String openid);

    int saveUser(User user);

    int updateById(User user);

    int deleteById(User user);
}
