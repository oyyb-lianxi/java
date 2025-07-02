
package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Result;
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
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/student")
public class studentController {

    @Autowired
    StudentService studentService;
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
    @GetMapping("/getStudentById/{studentId}")
    public ResponseEntity getStudentById(@PathVariable String studentId){
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
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
     * 根据条件筛选查询学生信息
     */
    @PostMapping("/getStudentsByConditions")
    public ResponseEntity getStudentsByConditions(@RequestBody Student student){
        Student result = studentService.getStudentsByConditions(student);
        return ResponseEntity.ok(result);
    }
}
