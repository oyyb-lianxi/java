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
    Student getStudentById(String userId);

    List<Student> getAllStudents();

    Student getStudentsByConditions(Student student);

    int deleteStudentById(String userId);
    int updateStudent(Student student);

    int deleteStudent(Student student);
}
