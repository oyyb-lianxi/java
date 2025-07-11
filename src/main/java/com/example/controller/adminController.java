package com.example.controller;

import com.example.mapper.AppointmentMapper;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.model.dto.AppointmentDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import com.example.model.vo.TeacherVo;
import com.example.service.AdminService;
import com.example.service.AppointmentService;
import com.example.service.StudentService;
import com.example.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    TeacherInfoService teacherInfoService;
    @Autowired
    AdminService adminService;

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentService appointmentService;
    /**
     * 统计数据
     */
    @GetMapping("/countTotals")
    public Result countTotals(){
        Result result =new Result();
        Map<String,Object> map =new HashMap();
        int countTeacher = teacherMapper.countAllTeacher();
        int countNewTodayTeacher = teacherMapper.countNewTodayTeacher();
        int countStudent = studentMapper.countAllStudent();
        int countNewTodayStudent = studentMapper.countNewTodayStudent();
        int countAppointment = appointmentMapper.countAllAppointment();
        int countNewTodayAppointment = appointmentMapper.countNewTodayAppointment();

        map.put("teacherCount",countTeacher);
        map.put("countNewTodayTeacher",countNewTodayTeacher);
        map.put("countStudent",countStudent);
        map.put("countNewTodayStudent",countNewTodayStudent);
        map.put("countAppointment",countAppointment);
        map.put("countNewTodayAppointment",countNewTodayAppointment);
        result.setCode(200);
        result.setData(map);
        return result;
    }
    @PostMapping("/studentToDo")
    public Result studentToDo(@RequestBody AppointmentDto studentAppointment){
        Result result =  adminService.adminQueryStudentToDo(studentAppointment);

        return result;
    }

}
