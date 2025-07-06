package com.example.mapper;

import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.model.dto.StudentDto;
import com.example.model.vo.StudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    int saveStudent(Student student);
    Integer countStudentsByConditions(StudentDto studentDto);
    StudentVo getStudentById(String userId);

    @Select("SELECT count(*) FROM user where type = '1' ")
    int countAllStudent();
    @Select("SELECT count(*) FROM user where type = '1' AND created >= CURDATE()")
    int countNewTodayStudent();
    List<Student> getAllStudents();

    List<StudentVo> getStudentsByConditions(StudentDto student);

    int deleteStudentById(String userId);
    int updateStudent(Student student);

    int deleteStudent(Student student);
}
