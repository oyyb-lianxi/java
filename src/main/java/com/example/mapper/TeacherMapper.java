package com.example.mapper;


import com.example.model.domain.Teacher;
import com.example.model.dto.TeacherDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();

    Teacher getTeacherById(String userId);
    List<Teacher> getAllTeachers();

    Teacher getTeachersByConditions(Teacher teacher);

    Teacher queryByUserId(String userId);

    int saveTeacher(Teacher teacher);

    int updateTeacher(Teacher teacher);
    int deleteTeacherById(String userId);

    int deleteTeacher(Teacher teacher);
    @InheritInverseConfiguration(name = "TeacherDto")
    Teacher teacherDTOToTeacher(TeacherDto carDTO);
}
