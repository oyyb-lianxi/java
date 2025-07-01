package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.model.domain.Appointment;
import com.example.model.dto.AppointmentDto;
import com.example.model.dto.TeacherDto;
import com.example.model.entity.Result;
import com.example.service.*;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class teacherController {

    @Autowired
    TeacherInfoService teacherInfoService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private TeacherTimeService teacherTimeService;

    /**
     * 查询所有老师信息
     */
    @GetMapping("/teacherList")
    public ResponseEntity selectAllTeacher(){
        List<Teacher> teachers = teacherInfoService.selectAll();
        return ResponseEntity.ok(teachers);
    }

    /**
     * 根据用户id查询老师信息
     */
    @GetMapping("/queryByUserId/{userId}")
    public ResponseEntity queryByUserId(@PathVariable String userId){
        Teacher teachers = teacherInfoService.queryByUserId(userId);
        return ResponseEntity.ok(teachers);
    }

    /**
     * 保存老师信息
     */
    @PostMapping("/saveTeacher")
    public ResponseEntity saveTeacher(@RequestBody TeacherDto teacherDto){
        //3、调用service
        System.out.println("saveUserInfo ==>"+teacherDto);
        Boolean aBoolean = teacherInfoService.saveTeacher(teacherDto);
        if(aBoolean){
            return ResponseEntity.ok(teacherDto);
        }
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 修改老师信息
     */
    @PostMapping("/updateTeacher")
    public ResponseEntity updateTeacher(@RequestBody Teacher teacher){
        Boolean aBoolean = teacherInfoService.updateTeacher(teacher);
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 删除老师信息
     */
    @PostMapping("/deleteTeacher")
    public ResponseEntity deleteTeacher(@RequestBody Teacher teacher){
        Boolean aBoolean = teacherInfoService.deleteTeacher(teacher);
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 老师待办
     */
    @PostMapping("/teacherToDo")
    public Result teacherToDo(@RequestBody AppointmentDto teacherAppointment){
        Appointment appointment = new Appointment();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(teacherAppointment.getAppointmentDateDto(), formatterDate);
        appointment.setAppointmentDate(localDateTime);
        appointment.setTeacherId(teacherAppointment.getTeacherId());
        Result result =new Result();
        List<Appointment> appointments = appointmentService.getAppointmentsByConditions(appointment);
        result.setCode(200);
        result.setData(appointments);
        return result;
    }

       /**
     * 老师删除可以预约时间
     * @param
     * @return
     */
   @PostMapping("/deleteTeacherTimeById/{id}")
   public Result deleteTeacherTimeById(@PathVariable Long id) {
       Result result=new Result();
           if(teacherTimeService.deleteTeacherTimeById(id)){
               result.setCode(200);
               result.setMsg("teacherTime删除成功");
               return result;
           }
               result.setMsg("删除失败，请联系管理员");
           return result;
   }

           /**
     * 老师确认预约
     * @param
     * @return
     */
   @PostMapping("/confirmAppointment/{id}")
   public Result confirmAppointment(@PathVariable Long id) {
       Result result=new Result();
           if(appointmentService.confirmAppointment(id)){
               result.setCode(200);
               result.setMsg("已经同意预约");
               return result;
           }
               result.setMsg("失败，请联系管理员");
           return result;
   }
}
