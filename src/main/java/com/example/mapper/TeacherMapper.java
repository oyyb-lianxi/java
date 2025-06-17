package com.example.mapper;


import com.example.model.domain.Teacher;
import com.example.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    Teacher getById(@Param("id") Long id);

    Teacher queryByUserId(Long userId);

    int saveTeacher(Teacher teacher);

    int updateTeacher(Teacher teacher);

    int deleteTeacher(Teacher teacher);
}
