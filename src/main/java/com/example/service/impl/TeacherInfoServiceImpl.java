package com.example.service.impl;

import com.example.mapper.AddressMapper;
import com.example.mapper.AppointmentMapper;
import com.example.mapper.TeacherMapper;
import com.example.mapper.UserMapper;
import com.example.model.domain.Address;
import com.example.model.domain.Appointment;
import com.example.model.domain.Teacher;
import com.example.model.domain.User;
import com.example.model.dto.TeacherDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import com.example.model.vo.TeacherVo;
import com.example.service.FileService;
import com.example.service.TeacherInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;

    //保存用户信息
    @Override
    public Boolean saveTeacher(TeacherDto teacherDto) {
        //保存地址信息
        Address address = getTeacherAddress(teacherDto);
        int i1 = addressMapper.saveUserAddress(address);
        if(i1 != 1){
            return false;
        }
        //保存老师信息
        int i = teacherMapper.saveTeacher(teacherDto);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<TeacherVo> getTeachersByConditions(TeacherDto teacherDto) {
        List<TeacherVo> teachersByConditions = teacherMapper.getTeachersByConditions(teacherDto);
        return teachersByConditions;
    }

    @Override
    public List<String> queryTeacherMonthToDo(String teacherId, String month){
        YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
         List<String> teachersByConditions = appointmentMapper.queryTeacherMonthToDo(teacherId,startOfMonth,endOfMonth);
            return teachersByConditions;
    }

    private static Address getTeacherAddress(TeacherDto teacherDto) {
        Address address = new Address();
        String province = teacherDto.getProvince();
        String city = teacherDto.getCity();
        String district = teacherDto.getDistrict();
        String detailedAddress = teacherDto.getDetailedAddress();
        String longitude = teacherDto.getLongitude();
        String latitude = teacherDto.getLatitude();
        String userId = teacherDto.getUserId();
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDetailedAddress(detailedAddress);
        address.setLongitude(longitude);
        address.setLatitude(latitude);
        address.setUserId(userId);
        address.setUserType("0");
        return address;
    }

    @Override
    public Boolean updateTeacher(TeacherDto teacherDto) {
        Address address = getTeacherAddress(teacherDto);
        int i1 = addressMapper.updateAddressById(address);
        if(i1 != 1){
            return false;
        }
        teacherDto.setLatitude("123");
        int i = teacherMapper.updateTeacher(teacherDto);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTeacher(Teacher teacher) {
        int i = teacherMapper.deleteTeacher(teacher);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<TeacherVo> getAllTeacherByPage(Integer page, Integer pageSize) {
        Integer offSet = (page-1) * pageSize;
        List<TeacherVo> teacherList = teacherMapper.getAllTeacherByPage(pageSize,offSet);
        log.info("teacherList"+teacherList);
        return teacherList;
    }

    @Override
    public Teacher queryByUserId(String userId) {
        Teacher teacherInfo = teacherMapper.queryByUserId(userId);
        log.info("teacherInfo");
        System.out.println("teacherInfo"+teacherInfo);
        return teacherInfo;
    }


}
