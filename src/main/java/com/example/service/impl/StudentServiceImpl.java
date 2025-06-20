package com.example.service.impl;

import com.example.mapper.StudentMapper;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Boolean saveStudent(Student student) {
        int i = studentMapper.saveStudent(student);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateStudent(Student student) {
        int i = studentMapper.updateStudent(student);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteStudentById(Student userInfo) {
        return null;
    }

    @Override
    public List<Student> selectAll() {
        return null;
    }

    @Override
    public Student queryByUserId(String userId) {
        return null;
    }


    @Override
    public Boolean deleteStudent(Student student) {
        int i = studentMapper.deleteStudent(student);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentMapper.getStudentById(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @Override
    public Student getStudentsByConditions(Student student) {
        return studentMapper.getStudentsByConditions(student);
    }
}
