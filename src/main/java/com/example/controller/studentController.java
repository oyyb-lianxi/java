
package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.service.StudentService;
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
@RequestMapping("/student")
public class teacherController {

    @Autowired
    StudentService studentService;
    /**
     * 查询所有学生信息
     */
    @GetMapping("/studentList")
    public ResponseEntity selectAllTeacher(){
        List<Student> students = studentService.selectAll();
        return ResponseEntity.ok(students);
    }

    /**
     * 根据用户id查询学生信息
     */
    @GetMapping("/queryByUserId/{userId}")
    public ResponseEntity queryByUserId(@PathVariable Long userId){
        Student students = studentService.queryByUserId(userId);
        return ResponseEntity.ok(students);
    }
}
