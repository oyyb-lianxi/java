package com.example.service;

import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.model.dto.StudentDto;

import java.util.List;

public interface StudentService {

    public Boolean saveStudent(StudentDto studentDto);

    public Boolean updateStudent(StudentDto studentDto);

    public Boolean deleteStudentById(Student userInfo);

    public List<Student> selectAll();

    public Student queryByUserId(String userId);

    public Boolean deleteStudent(Student userInfo);

    public Student getStudentById(String studentId);

    public List<Student> getAllStudents();

    public Student getStudentsByConditions(Student student);
}
