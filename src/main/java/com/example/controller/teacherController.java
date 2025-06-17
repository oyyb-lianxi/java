package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.service.TeacherInfoService;
import com.example.service.WechatToolsService;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.service.HttpUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class teacherController {

    @Autowired
    TeacherInfoService teacherInfoService;
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
    public ResponseEntity queryByUserId(@PathVariable Long userId){
        Teacher teachers = teacherInfoService.queryByUserId(userId);
        return ResponseEntity.ok(teachers);
    }
}
