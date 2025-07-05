package com.example.service.impl;

import com.example.mapper.AddressMapper;
import com.example.mapper.StudentMapper;
import com.example.model.domain.Address;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.model.dto.StudentDto;
import com.example.model.dto.TeacherDto;
import com.example.model.vo.StudentVo;
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
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public Boolean saveStudent(StudentDto studentDto) {
        Address address = getStudentAddress(studentDto);
        int i1 = addressMapper.saveUserAddress(address);
        if(i1 != 1){
            return false;
        }
        int i = studentMapper.saveStudent(studentDto);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateStudent(StudentDto studentDto) {
        Address address = getStudentAddress(studentDto);
        int i1 = addressMapper.updateAddressById(address);
        if(i1 != 1){
            return false;
        }
        int i = studentMapper.updateStudent(studentDto);
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
    public List<StudentVo> getStudentsByConditions(StudentDto student) {
        return studentMapper.getStudentsByConditions(student);
    }

    private static Address getStudentAddress(StudentDto studentDto) {
        Address address = new Address();
        String province = studentDto.getProvince();
        String city = studentDto.getCity();
        String district = studentDto.getDistrict();
        String detailedAddress = studentDto.getDetailedAddress();
        String longitude = studentDto.getLongitude();
        String latitude = studentDto.getLatitude();
        String userId = studentDto.getUserId();
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDetailedAddress(detailedAddress);
        address.setLongitude(longitude);
        address.setLatitude(latitude);
        address.setUserId(userId);
        address.setUserType("1");
        return address;
    }
}
