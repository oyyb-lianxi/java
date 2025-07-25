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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 老师修改预约时间
     * @param
     * @return
     */
   @PostMapping("/updateTeacherTime")
   public Result updateTeacherTime(@RequestBody TeacherTime teacherTime) {
       Result result=new Result();
           if(teacherTimeService.updateTeacherTime(teacherTime)){
               result.setCode(200);
               result.setMsg("teacherTime修改成功");
               return result;
           }else {
               result.setMsg("修改失败，请联系管理员");
           }
       return result;
   }

    /**
     * 查询老师空闲时间
     * @param teacherTime
     * @return
     */
    @PostMapping("/getAllTeacherTimeByTeacherId")
    public Result getAllTeacherTimeByTeacherId(@RequestBody TeacherTime teacherTime) {
        Result result=new Result();
        List<TeacherTime> teacherTimeByConditions = teacherTimeService.getAllTeacherTimeByTeacherId(teacherTime.getTeacherId());
        result.setData(teacherTimeByConditions);
        result.setCode(200);
        return result;
    }

    /**
     * 学生查看老师空闲时间
     * @param appointmentDto
     * @return
     */
    @PostMapping("/getTeacherFreeTime")
    public Result getTeacherFreeTime(@RequestBody AppointmentDto appointmentDto) {
        Result result = new Result();
        List<TeacherTime> existsList = new ArrayList<>();
        List<TeacherTime> noExistsList = new ArrayList<>();
        Map<String,List<TeacherTime>> resultMap = new HashMap();
        //查询该老师自己设置的预约时间
        List<TeacherTime> timeByTeacherId = teacherTimeService.getAllTeacherTimeByTeacherId(appointmentDto.getTeacherId());
        //查询该老师自己设置的预约时间段是否存在学生已经预约成功
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (TeacherTime teacherFreeTime : timeByTeacherId) {
            Appointment appointment =new Appointment();
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentDto.getAppointmentDateDto(), formatterDate);
            appointment.setAppointmentDate(localDateTime);
            appointment.setTeacherId(appointmentDto.getTeacherId());
            LocalTime startTime = LocalTime.parse(teacherFreeTime.getStartTime());
            LocalTime endTime = LocalTime.parse(teacherFreeTime.getEndTime());
            LocalDate localDate = localDateTime.toLocalDate();
            LocalDateTime appointmentStartTime = LocalDateTime.of(localDate, startTime);
            LocalDateTime appointmentEndTime = LocalDateTime.of(localDate, endTime);

            appointment.setAppointmentStartTime(appointmentStartTime);
            appointment.setAppointmentEndTime(appointmentEndTime);
            boolean existAppointment = appointmentService.isTimeSlotAvailable(appointment);
            if(existAppointment){
                noExistsList.add(teacherFreeTime);
            }else {
                existsList.add(teacherFreeTime);
            }
        }
        resultMap.put("noExists",noExistsList);
        resultMap.put("exists",existsList);
        result.setCode(200);
        result.setData(resultMap);
        return result;
    }

    /**
     * 学生预约
     * @param appointmentDto
     * @return
     */
    @PostMapping("/studentAppointments")
    public Result studentAppointments(@RequestBody AppointmentDto appointmentDto) {
        Result result=new Result();
        Appointment appointment = new Appointment();
        Appointment myAppointment = new Appointment();
        TeacherTime teacherTime =new TeacherTime();
        String teacherId = appointmentDto.getTeacherId();
        String studentId = appointmentDto.getStudentId();
        teacherTime.setTeacherId(teacherId);
        // 定义时间格式
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将 LocalDateTime 转换为字符串
        teacherTime.setStartTime(appointmentDto.getAppointmentStartTimeDto());
        teacherTime.setEndTime(appointmentDto.getAppointmentEndTimeDto());
        //存在对应的老师空闲时间返回false
        boolean timeSlotAvailable = teacherTimeService.isTimeSlotAvailable(teacherTime);
        if(!timeSlotAvailable){
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentDto.getAppointmentDateDto(),formatterDate);
            appointment.setAppointmentDate(localDateTime);
            LocalTime startTime = LocalTime.parse(appointmentDto.getAppointmentStartTimeDto());
            LocalTime endTime = LocalTime.parse(appointmentDto.getAppointmentEndTimeDto());
            LocalDate localDate = localDateTime.toLocalDate();
            LocalDateTime appointmentStartTime = LocalDateTime.of(localDate, startTime);
            LocalDateTime appointmentEndTime = LocalDateTime.of(localDate, endTime);

            appointment.setAppointmentStartTime(appointmentStartTime);
            appointment.setAppointmentEndTime(appointmentEndTime);
            //判断是否已经被其他学生预约
            appointment.setTeacherId(teacherId);
            boolean existOtherAppointment = appointmentService.isTimeSlotAvailable(appointment);

            myAppointment.setStudentId(studentId);
            myAppointment.setAppointmentDate(localDateTime);
            myAppointment.setAppointmentStartTime(appointmentStartTime);
            myAppointment.setAppointmentEndTime(appointmentEndTime);
            boolean existMyAppointment = appointmentService.isTimeSlotAvailable(myAppointment);
            if(existOtherAppointment && existMyAppointment){
                appointment.setStudentId(appointmentDto.getStudentId());
                appointment.setAdminPhone(appointmentDto.getAdminPhone());
                appointment.setStatus("PENDING");
                appointment.setLocation(appointmentDto.getLocation());
                appointmentService.createAppointment(appointment);
                result.setCode(200);
                result.setMsg("预约成功");
                return result;
            }
        }
        result.setCode(403);
        result.setMsg("该时间段已有预约");
        return result;
    }


}


