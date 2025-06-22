package com.example.service;

import com.example.model.domain.Appointment;
import com.example.model.domain.TeacherTime;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherTimeService {
    public boolean isTimeSlotAvailable(String teacherId, String startTime, String endTime);
    public boolean createTeacherTime(TeacherTime teacherTime);
    public List<TeacherTime> getTeacherTimeByConditions(TeacherTime teacherTime);


}
