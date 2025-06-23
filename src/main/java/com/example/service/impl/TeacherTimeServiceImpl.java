package com.example.service.impl;

import com.example.mapper.TeacherTimeMapper;
import com.example.model.domain.Appointment;
import com.example.model.domain.TeacherTime;
import com.example.service.TeacherTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherTimeServiceImpl implements TeacherTimeService {
    @Autowired
    private TeacherTimeMapper teacherTimeMapper;

    @Override
    public boolean isTimeSlotAvailable(TeacherTime teacherTime) {
        return !teacherTimeMapper.existsByTeacherTime(teacherTime);
    }

    @Override
    public boolean createTeacherTime(TeacherTime teacherTime) {
        int i = teacherTimeMapper.saveTeacherTime(teacherTime);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTeacherTimeById(Long teacherTimeId) {
        int i = teacherTimeMapper.deleteTeacherTimeById(teacherTimeId);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTeacherTime(TeacherTime teacherTime) {
        int i = teacherTimeMapper.updateTeacherTime(teacherTime);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<TeacherTime> getTeacherTimeByConditions(TeacherTime teacherTime) {
        return teacherTimeMapper.getAllTeacherTimeByTeacherId(teacherTime);
    }
}
