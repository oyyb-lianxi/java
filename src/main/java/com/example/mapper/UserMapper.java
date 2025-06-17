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

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(@Param("id") Long id);

    int saveUser(User user);

    int updateById(User user);

    int deleteById(User user);
}
