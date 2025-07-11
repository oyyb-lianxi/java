package com.example.controller;

import com.example.mapper.AddressMapper;
import com.example.mapper.AppointmentMapper;
import com.example.mapper.TeacherMapper;
import com.example.model.CommonUtils.JwtUtils;
import com.example.model.domain.*;
import com.example.model.dto.AppointmentDto;
import com.example.model.dto.TeacherDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import com.example.model.vo.TeacherVo;
import com.example.service.*;
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
@RequestMapping("/teacher")
public class teacherController {

    @Autowired
    TeacherInfoService teacherInfoService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private TeacherTimeService teacherTimeService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 查询所有老师信息
     */
    @GetMapping("/teacherList/{page}/{pageSize}")
    public Result selectAllTeacher(@PathVariable Integer page,@PathVariable Integer pageSize){
        Result result =new Result();
        Map<String,Object> map =new HashMap();
        List<TeacherVo> teachers = teacherInfoService.getAllTeacherByPage(page,pageSize);
        int countTeacher = teacherMapper.countAllTeacher();
        map.put("teachers",teachers);
        map.put("total",countTeacher);
        result.setCode(200);
        result.setData(map);
        return result;
    }

    /**
     * 根据用户id查询老师信息
     */
    @GetMapping("/queryByUserId/{userId}")
    public Result queryByUserId(@PathVariable String userId){
        Result result =new Result();
        Map<String,Object> map =new HashMap();
        TeacherDto teacherDto = new TeacherDto();
        Teacher teachers = teacherInfoService.queryByUserId(userId);
        Address userAddressById = addressMapper.getUserAddressById(userId);
        Integer countAppointment = appointmentMapper.getAppointmentByTeacherId(userId);
        map.put("teacher",teachers);
        map.put("teacherAddress",userAddressById);
        map.put("countAppointment",countAppointment);
        result.setCode(200);
        result.setData(map);
        return result;
    }
    /**
     * 按条件查询老师信息
     */
    @PostMapping("/getTeachersByConditions/{page}/{pageSize}")
    public Result getTeachersByConditions(@RequestBody TeacherDto teacherDto,
                                          @PathVariable Integer page,@PathVariable Integer pageSize){
        Result result =new Result();
        log.info(teacherDto+"queryTeacher");
        Map<String,Object> map =new HashMap();
        teacherDto.setOffSet((page-1) * pageSize);
        teacherDto.setPageSize(pageSize);
        List<TeacherVo> teachersByConditions = teacherInfoService.getTeachersByConditions(teacherDto);
        Integer  countTeachersByConditions= teacherMapper.countTeachersByConditions(teacherDto);
        for (TeacherVo teachersByCondition : teachersByConditions) {
            StringBuffer sb = new StringBuffer();
            List<TeacherTime> allTeacherTimeByTeacherId = teacherTimeService.getAllTeacherTimeByTeacherId(teachersByCondition.getUserId());
            for (TeacherTime teacherTime : allTeacherTimeByTeacherId) {
                sb.append(teacherTime.getStartTime());
                sb.append("-");
                sb.append(teacherTime.getEndTime());
                sb.append(";");
            }
            teachersByCondition.setTeacherFreeTime(sb.toString());
        }

        map.put("teachers",teachersByConditions);
        map.put("total",countTeachersByConditions);
        result.setCode(200);
        result.setData(map);
        return result;
    }

    /**
     * 保存老师信息
     */
    @PostMapping("/saveTeacher")
    public Result saveTeacher(@RequestBody TeacherDto teacherDto){
        Result result =new Result();
       log.info("saveUserInfo ==>"+teacherDto);
        Boolean aBoolean = teacherInfoService.saveTeacher(teacherDto);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("保存成功");
        }else {
            result.setMsg("保存失败");
        }
        return result;
    }

    /**
     * 修改老师信息
     */
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody TeacherDto teacher){
        Result result =new Result();
        Boolean aBoolean = teacherInfoService.updateTeacher(teacher);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("修改成功");
        }else {
            result.setMsg("修改失败");
        }
        return result;
    }
    
    /**
     * 修改老师地址信息
     */
    @PostMapping("/updateTeacherAddress")
    public Result updateTeacherAddress(@RequestBody Address address){
        Result result =new Result();
        addressMapper.updateAddressById(address);
        result.setCode(200);
        result.setMsg("修改成功");
        return result;
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
     * 按日期查看老师待办
     */
    @PostMapping("/teacherToDo")
    public Result teacherToDo(@RequestBody AppointmentDto teacherAppointment){
        String appointmentDateDto = teacherAppointment.getAppointmentDateDto();
        String appointmentCreatedDto = teacherAppointment.getAppointmentCreatedDto();
        if(teacherAppointment.getPage()!=null && teacherAppointment.getPageSize()!= null){
            teacherAppointment.setOffSet((teacherAppointment.getPage()-1) * teacherAppointment.getPageSize());
        }
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(appointmentDateDto!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentDateDto, formatterDate);
            teacherAppointment.setAppointmentDate(localDateTime);
        }
        if(appointmentCreatedDto!=null){
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentCreatedDto, formatterDate);
            teacherAppointment.setCreated(localDateTime);
        }

        Result result =new Result();
        List<AppointmentVo> appointments = appointmentService.getAppointmentsByConditions(teacherAppointment);
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
            List<AppointmentVo> newAppointments = appointmentService.getAppointmentsByConditions(teacherAppointment);
            result.setData(newAppointments);
        }else {
            result.setData(appointments);
        }
        return result;
    }

    /**
     * 按月查看老师待办
     */
    @PostMapping("/teacherMonthToDo")
    public Result teacherMonthToDo(@RequestBody AppointmentDto teacherAppointment){
         Result result =new Result();
        String teacherId = teacherAppointment.getTeacherId();

        String appointmentDateDto = teacherAppointment.getAppointmentDateDto();
        List<String> toDoList = teacherInfoService.queryTeacherMonthToDo(teacherId, appointmentDateDto);
        
        result.setCode(200);
        result.setData(toDoList);
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
   @PostMapping("/confirmAppointment")
   public Result confirmAppointment(@RequestBody AppointmentDto appointmentDto) {
       Result result=new Result();
           if(appointmentService.confirmAppointment(appointmentDto)){
               result.setCode(200);
               result.setMsg("已经同意预约");
               return result;
           }
               result.setMsg("失败，请联系管理员");
           return result;
   }
}
