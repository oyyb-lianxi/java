package com.example.controller;

import com.example.mapper.AppointmentMapper;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.model.domain.Notice;
import com.example.model.domain.ProblemHelp;
import com.example.model.domain.Teacher;
import com.example.model.dto.AppointmentDto;
import com.example.model.dto.ProblemHelpDto;
import com.example.model.dto.TeacherDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import com.example.model.vo.TeacherVo;
import com.example.service.*;
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
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ProblemHelpService problemHelpService;
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

    /**
     * 管理员查看学生预约列表
     * @param studentAppointment
     * @return
     */
    @PostMapping("/studentToDo")
    public Result studentToDo(@RequestBody AppointmentDto studentAppointment){
        Result result =  adminService.adminQueryStudentToDo(studentAppointment);

        return result;
    }

    /**
     * 取消预约
     * @param appointmentDto
     * @return
     */
    @PostMapping("/cancelAppointment")
    public Result cancelAppointment(@RequestBody AppointmentDto appointmentDto){
        Result result =  adminService.cancelAppointment(appointmentDto);

        return result;
    }

    /**
     * 老师升级
     * @param teacher
     * @return
     */
    @PostMapping("/upgradeTeacher")
    public Result upgradeTeacher(@RequestBody TeacherDto teacher){
        Result result =  teacherInfoService.upgradeTeacher(teacher);

        return result;
    }

    /**
     * 老师降级
     * @param teacher
     * @return
     */
    @PostMapping("/demoteTeacher")
    public Result demoteTeacher(@RequestBody TeacherDto teacher){
        Result result =  teacherInfoService.demoteTeacher(teacher);

        return result;
    }

    /**
     * 发布通知
     * @param notice
     * @return
     */
    @PostMapping("/publishNotice")
    public Result publishNotice(@RequestBody Notice notice){
        Result result =  noticeService.publishNotice(notice);
        return result;
    }

    /**
     * 修改通知
     * @param notice
     * @return
     */
    @PostMapping("/updateNotice")
    public Result updateNotice(@RequestBody Notice notice){
        Result result =  noticeService.updateNotice(notice);
        return result;
    }

    /**
     * 删除通知
     * @param notice
     * @return
     */
    @PostMapping("/deleteNotice")
    public Result deleteNotice(@RequestBody Notice notice){
        Result result =  noticeService.deleteNotice(notice);
        return result;
    }

    /**
     * 查看通知
     * @param
     * @return
     */
    @GetMapping("/findNoticeList")
    public Result findNoticeList(){
        Result result =  noticeService.findNoticeList();
        return result;
    }


    /**
     * 发布帮助信息Publish help information
     * @param problemHelp
     * @return
     */
    @PostMapping("/publishHelpInformation")
    public Result publishHelpInformation(@RequestBody ProblemHelp problemHelp){
        Result result =  problemHelpService.publishHelpInformation(problemHelp);
        return result;
    }

    /**
     * 修改帮助信息
     * @param problemHelp
     * @return
     */
    @PostMapping("/updateProblemHelpById")
    public Result updateProblemHelpById(@RequestBody ProblemHelp problemHelp){
        Result result =  problemHelpService.updateProblemHelpById(problemHelp);
        return result;
    }

    /**
     * 删除帮助信息
     * @param problemHelp
     * @return
     */
    @PostMapping("/deleteProblemHelpById")
    public Result deleteProblemHelpById(@RequestBody ProblemHelp problemHelp){
        Result result =  problemHelpService.deleteProblemHelpById(problemHelp);
        return result;
    }

    /**
     * 查看帮助信息列表
     * @param
     * @return
     */
    @PostMapping("/getProblemHelpByConditions")
    public Result getProblemHelpByConditions(@RequestBody ProblemHelpDto problemHelp){
        Result result =  problemHelpService.getProblemHelpByConditions(problemHelp);
        return result;
    }

    /**
     * 查看帮助信息详情
     * @param
     * @return
     */
    @GetMapping("/getProblemHelpById/{id}")
    public Result getProblemHelpById(@PathVariable Integer id){
        Result result =  problemHelpService.getProblemHelpById(id);
        return result;
    }
}
