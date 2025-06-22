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
    public boolean isTimeSlotAvailable(String teacherId, String startTime, String endTime) {
        return !teacherTimeMapper.existsByTeacherTime( teacherId,  startTime,  endTime);
    }

    @Override
    public boolean createTeacherTime(TeacherTime teacherTime) {
        teacherTimeMapper.saveTeacherTime(teacherTime);
        return true;
    }

    @Override
    public List<TeacherTime> getTeacherTimeByConditions(TeacherTime teacherTime) {
        return teacherTimeMapper.getAllTeacherTimeByTeacherId(teacherTime);
    }
}
