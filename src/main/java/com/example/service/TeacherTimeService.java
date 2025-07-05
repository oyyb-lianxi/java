package com.example.service;

import com.example.model.domain.Appointment;
import com.example.model.domain.TeacherTime;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherTimeService {
    public boolean isTimeSlotAvailable(TeacherTime teacherTime);
    public boolean createTeacherTime(TeacherTime teacherTime);
    public List<TeacherTime> getAllTeacherTimeByTeacherId(String teacherId);
    public boolean deleteTeacherTimeById(Long teacherTimeId);
    public boolean updateTeacherTime(TeacherTime teacherTime);

}
