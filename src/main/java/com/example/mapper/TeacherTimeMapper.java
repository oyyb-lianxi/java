package com.example.mapper;

import com.example.model.domain.Appointment;
import com.example.model.domain.TeacherTime;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TeacherTimeMapper {
    boolean existsByTeacherTime(TeacherTime teacherTime);
    int saveTeacherTime(TeacherTime teacherTime);
    int deleteTeacherTimeById(Long id);
    int updateTeacherTime(TeacherTime teacherTime);
    List<TeacherTime>  getAllTeacherTimeByTeacherId(TeacherTime teacherTime);
}
