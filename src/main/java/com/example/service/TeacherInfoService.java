package com.example.service;

import com.example.model.domain.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TeacherInfoService {

    public Boolean saveTeacher(Teacher userInfo);

    public Boolean updateTeacher(Teacher userInfo);

    public Boolean deleteTeacher(Teacher userInfo);

    public List<Teacher> selectAll();

    public Teacher queryByUserId(Long userId);

}
