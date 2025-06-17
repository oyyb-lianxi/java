package com.example.service.impl;

import com.example.mapper.TeacherMapper;
import com.example.mapper.UserMapper;
import com.example.model.domain.Teacher;
import com.example.model.domain.User;
import com.example.service.TeacherInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private TeacherMapper teacherMapper;

    //保存用户信息
    @Override
    public Boolean saveTeacher(Teacher teacher) {
        int i = teacherMapper.saveTeacher(teacher);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateTeacher(Teacher teacher) {
        int i = teacherMapper.updateTeacher(teacher);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTeacher(Teacher teacher) {
        int i = teacherMapper.deleteTeacher(teacher);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<Teacher> selectAll() {
        List<Teacher> teacherList = teacherMapper.selectAll();
        log.info("aaaaaaa");
        System.out.println("teacherList"+teacherList);
        return teacherList;
    }

    @Override
    public Teacher queryByUserId(Long userId) {
        Teacher teacherInfo = teacherMapper.queryByUserId(userId);
        log.info("teacherInfo");
        System.out.println("teacherInfo"+teacherInfo);
        return teacherInfo;
    }


}
