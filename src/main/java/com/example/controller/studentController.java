
package com.example.controller;

import com.example.mapper.AddressMapper;
import com.example.mapper.StudentMapper;
import com.example.model.CommonUtils.JwtUtils;
import com.example.model.domain.*;
import com.example.model.dto.AppointmentDto;
import com.example.model.dto.StudentDto;
import com.example.model.dto.TeacherDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import com.example.model.vo.StudentVo;
import com.example.model.vo.TeacherVo;
import com.example.service.AppointmentService;
import com.example.service.StudentService;
import com.example.service.WechatToolsService;
import com.example.service.HttpUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/student")
public class studentController {

    @Autowired
    StudentService studentService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AppointmentService appointmentService;
    /**
     * 保存学生信息
     */
    @PostMapping("/saveStudent")
    public Result saveStudent(@RequestBody StudentDto student){
        Result result =new Result();
        System.out.println("saveUserInfo ==>"+student);
        Boolean aBoolean = studentService.saveStudent(student);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("保存成功");
        }else {
            result.setMsg("保存失败");
        }
        return result;
    }

    /**
     * 修改学生信息
     */
    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody StudentDto student){
        Result result =new Result();
        Boolean aBoolean = studentService.updateStudent(student);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("修改成功");
        }else {
            result.setMsg("修改失败");
        }
        return result;
    }

    /**
     * 删除学生信息
     */
    @PostMapping("/deleteStudent")
    public ResponseEntity deleteStudent(@RequestBody Student student){
        Boolean aBoolean = studentService.deleteStudent(student);
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 根据学生id查询学生信息
     */
    @PostMapping("/getStudentById")
    public Result getStudentById(@RequestBody Student Student){
        Result result =new Result();
        Map<String,Object> map =new HashMap();
        StudentVo student = studentService.getStudentById(Student.getUserId());
        map.put("student",student);
        result.setCode(200);
        result.setData(map);
        return result;
    }

    /**
     * 查询所有学生信息
     */
    @GetMapping("/getAllStudents")
    public ResponseEntity getStudentById(){
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * 学生确认完成
     * @param
     * @return
     */
    @PostMapping("/successFinishAppointment/{id}")
    public Result confirmAppointment(@PathVariable Long id) {
        Result result=new Result();
        if(appointmentService.successFinishAppointment(id)){
            result.setCode(200);
            result.setMsg("已经同意预约");
            return result;
        }
        result.setMsg("失败，请联系管理员");
        return result;
    }
    /**
     * 按条件查询学生信息
     */
    @PostMapping("/getStudentsByConditions/{page}/{pageSize}")
    public Result getStudentsByConditions(@RequestBody StudentDto studentDto,
                                          @PathVariable Integer page,@PathVariable Integer pageSize){
        Result result =new Result();
        log.info(studentDto+"studentDto");
        Map<String,Object> map =new HashMap();
        studentDto.setOffSet((page-1) * pageSize);
        studentDto.setPageSize(pageSize);
        List<StudentVo> studentsByConditions = studentService.getStudentsByConditions(studentDto);
        if(page!=null){
            Integer  countTeachersByConditions= studentMapper.countStudentsByConditions(studentDto);
            map.put("total",countTeachersByConditions);
        }
        map.put("students",studentsByConditions);
        result.setCode(200);
        result.setData(map);
        return result;
    }
    /**
     * 学生预约列表
     */
    @PostMapping("/studentToDo")
    public Result studentToDo(@RequestBody AppointmentDto studentAppointment){
        String appointmentDateDto = studentAppointment.getAppointmentDateDto();
        String appointmentCreatedDto = studentAppointment.getAppointmentCreatedDto();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(studentAppointment.getPage()!=null && studentAppointment.getPageSize()!= null){
            studentAppointment.setOffSet((studentAppointment.getPage()-1) * studentAppointment.getPageSize());
        }
        if(appointmentDateDto!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentDateDto, formatterDate);
            studentAppointment.setAppointmentDate(localDateTime);
        }
        if(appointmentCreatedDto!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentCreatedDto, formatterDate);
            studentAppointment.setCreated(localDateTime);
        }

        Result result =new Result();
        List<AppointmentVo> appointments = appointmentService.getAppointmentsByConditions(studentAppointment);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        int countCancel = 0;
        //取消已经过期预约
        for (AppointmentVo appointment1 : appointments) {
            if(new Timestamp(appointment1.getAppointmentEndTime()).before(currentTimestamp) &&
                    appointment1.getStatus().equals("PENDING")){
                appointmentService.cancelAppointment(appointment1.getId());
                countCancel++;
            }
        }
        result.setCode(200);

        if(countCancel>0){
            List<AppointmentVo> newAppointments = appointmentService.getAppointmentsByConditions(studentAppointment);
            result.setData(newAppointments);
        }else {
            result.setData(appointments);
        }
        return result;
    }
}
