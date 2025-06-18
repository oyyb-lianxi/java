package com.example.mapper;

import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    int saveStudent(Student student);
    Student getStudentById(Long studentId);

    List<Student> getAllStudents(Long studentId);

    Student getStudentsByConditions(Student student);

    int updateStudent();

    int deleteStudentById(Long studentId);
}
