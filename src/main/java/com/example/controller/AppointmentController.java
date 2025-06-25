package com.example.controller;

import com.example.model.domain.Appointment;
import com.example.model.domain.TeacherTime;
import com.example.model.dto.AppointmentDto;
import com.example.model.entity.Result;
import com.example.service.AppointmentService;
import com.example.service.TeacherTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
   public Result createTeacherTime(@RequestBody TeacherTime teacherTime) {
       Result result=new Result();
       if (teacherTimeService.isTimeSlotAvailable(teacherTime)) {
           if(teacherTimeService.createTeacherTime(teacherTime)){
               result.setCode(200);
               result.setMsg("teacherTime created successfully");
               return result;
           }else {
               result.setMsg("创建失败，请联系管理员");
           }

       }
       result.setMsg("Selected time slot is not available");
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
     * 老师修改预约时间
     * @param
     * @return
     */
   @PostMapping("/updateTeacherTime")
   public Result updateTeacherTime(@RequestBody TeacherTime teacherTime) {
       Result result=new Result();
       if (!teacherTimeService.isTimeSlotAvailable(teacherTime)) {
           if(teacherTimeService.updateTeacherTime(teacherTime)){
               result.setCode(200);
               result.setMsg("teacherTime修改成功");
               return result;
           }else {
               result.setMsg("修改失败，请联系管理员");
           }

       }
       result.setMsg("Selected time slot is not available");
       return result;
   }

    @PostMapping("/getTeTeacherTimeByConditions")
    public Result getTeTeacherTimeByConditions(@RequestBody TeacherTime teacherTime) {
        Result result=new Result();
        List<TeacherTime> teacherTimeByConditions = teacherTimeService.getTeacherTimeByConditions(teacherTime);
        result.setData(teacherTimeByConditions);
        result.setCode(200);
        return result;
    }

    /**
     * 学生查看老师空闲时间
     * @param appointment
     * @return
     */
    @PostMapping("/getAppointmentsByConditions")
    public List<Appointment> getAppointmentsByUserId(@RequestBody Appointment appointment) {
        return appointmentService.getAppointmentsByConditions(appointment);
    }

    /**
     * 学生预约
     * @param appointmentDto
     * @return
     */
    @PostMapping("/studentAppointments")
    public List<Appointment> studentAppointments(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        TeacherTime teacherTime =new TeacherTime();
        teacherTime.setTeacherId(appointmentDto.getTeacherId());
        // 定义时间格式
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        // 将 LocalDateTime 转换为字符串
        teacherTime.setStartTime(appointmentDto.getAppointmentStartTimeDto());
        teacherTime.setEndTime(appointmentDto.getAppointmentEndTimeDto());
        //存在对应的老师空闲时间返回false
        boolean timeSlotAvailable = teacherTimeService.isTimeSlotAvailable(teacherTime);
        if(!timeSlotAvailable){

//            appointment.setAppointmentDate(appointmentDto.getAppointmentDateDto());
//            appointment.setAppointmentStartTime(formatterTime.format(appointmentDto.getAppointmentStartTimeDto()));
            //判断是否已经被其他学生预约

            appointmentService.getAppointmentsByConditions(appointment);
        }
        return appointmentService.getAppointmentsByConditions(appointment);
    }

}


