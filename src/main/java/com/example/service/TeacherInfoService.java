package com.example.service;

import com.example.model.domain.Teacher;
import com.example.model.dto.TeacherDto;
import com.example.model.vo.TeacherVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TeacherInfoService {

    public Boolean saveTeacher(TeacherDto teacherDto);
    public List<TeacherVo> getTeachersByConditions(TeacherDto teacherDto);
    public List<String> queryTeacherMonthToDo(String teacherId, String month);
    public Boolean updateTeacher(TeacherDto userInfo);

    public Boolean deleteTeacher(Teacher userInfo);

    public List<TeacherVo> getAllTeacherByPage(Integer page, Integer pageSize);

    public Teacher queryByUserId(String userId);

}
