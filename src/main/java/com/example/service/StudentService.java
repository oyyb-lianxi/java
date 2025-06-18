package com.example.service;

import com.example.model.domain.Student;
import com.example.model.domain.Teacher;

import java.util.List;

public interface StudentService {

    public Boolean saveStudent(Student userInfo);

    public Boolean updateStudent(Student userInfo);

    public Boolean deleteStudentById(Student userInfo);

    public List<Teacher> selectAll();

    public Student queryByUserId(Long userId);
}
