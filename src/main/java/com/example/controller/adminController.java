package com.example.controller;

import com.example.mapper.AppointmentMapper;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.model.entity.Result;
import com.example.model.vo.TeacherVo;
import com.example.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    TeacherInfoService teacherInfoService;

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
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


}
