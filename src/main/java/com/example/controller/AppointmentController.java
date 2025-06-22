package com.example.controller;

import com.example.model.domain.Appointment;
import com.example.model.domain.TeacherTime;
import com.example.service.AppointmentService;
import com.example.service.TeacherTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TeacherTimeService teacherTimeService;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    /**
     * 老师创建可以预约时间
     * @param teacherTime
     * @return
     */
   @PostMapping("/createTeacherTime")
   public ResponseEntity<?> createTeacherTime(@RequestBody TeacherTime teacherTime) {
       String startTime = teacherTime.getStartTime();
       String endTime = teacherTime.getEndTime();
       String teacherId = teacherTime.getTeacherId();
       if (teacherTimeService.isTimeSlotAvailable(teacherId,startTime,endTime)) {
           teacherTimeService.createTeacherTime(teacherTime);
           return ResponseEntity.ok("Appointment created successfully");
       } else {
           return ResponseEntity.ok("Selected time slot is not available");
       }
   }

    @PostMapping("/getTeTeacherTimeByConditions")
    public List<TeacherTime> getTeTeacherTimeByConditions(@RequestBody TeacherTime teacherTime) {
        return teacherTimeService.getTeacherTimeByConditions(teacherTime);

    }

    @PostMapping("/getAppointmentsByConditions")
    public List<Appointment> getAppointmentsByUserId(@RequestBody Appointment appointment) {
        return appointmentService.getAppointmentsByConditions(appointment);
    }
}


