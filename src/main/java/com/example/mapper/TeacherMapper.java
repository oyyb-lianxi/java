package com.example.mapper;


import com.example.model.domain.Teacher;
import com.example.model.dto.TeacherDto;
import com.example.model.vo.TeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();
    @Select("SELECT count(*) FROM user where type = '0'")
    int countAllTeacher();
    @Select("SELECT count(*) FROM user where type = '0' AND created >= CURDATE()")
    int countNewTodayTeacher();

    Teacher getTeacherById(String userId);
    List<TeacherVo> getAllTeacherByPage(Integer pageSize, Integer offSet);

    List<TeacherVo> getTeachersByConditions(TeacherDto teacher);
    Integer countTeachersByConditions(TeacherDto teacher);

    Teacher queryByUserId(String userId);

    int saveTeacher(Teacher teacher);

    int updateTeacher(Teacher teacher);
    int deleteTeacherById(String userId);

    int deleteTeacher(Teacher teacher);
    @InheritInverseConfiguration(name = "TeacherDto")
    Teacher teacherDTOToTeacher(TeacherDto teacherDto);

    int upgradeTeacher(Teacher teacher);
}
