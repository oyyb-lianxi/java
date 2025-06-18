package com.example.service.impl;

import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Override
    public Boolean saveStudent(Student userInfo) {
        return null;
    }

    @Override
    public Boolean updateStudent(Student userInfo) {
        return null;
    }

    @Override
    public Boolean deleteStudentById(Student userInfo) {
        return null;
    }

    @Override
    public List<Teacher> selectAll() {
        return null;
    }

    @Override
    public Student queryByUserId(Long userId) {
        return null;
    }
}
