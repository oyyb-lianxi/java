package com.example.mapper;


import com.example.model.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();

    Teacher getTeacherById(Long id);
    List<Teacher> getAllTeachers();

    Teacher getTeachersByConditions(Teacher teacher);

    Teacher queryByUserId(Long userId);

    int saveTeacher(Teacher teacher);

    int updateTeacher(Teacher teacher);
    int deleteTeacherById(Long id);

    int deleteTeacher(Teacher teacher);

}
