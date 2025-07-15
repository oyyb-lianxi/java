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
        if(address!=null){
            int i1 = addressMapper.updateAddressById(address);
            if(i1 != 1){
                return false;
            }
        }
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

    @Override
    public Result upgradeTeacher(TeacherDto teacherDto) {
        Result result =new Result();
        Integer level = teacherDto.getLevel();
        String userId = teacherDto.getUserId();
        Teacher teacher = new Teacher();
        Integer newLevel = level + 1;
        if(newLevel > 5){
            result.setMsg("已经是最高级");
            result.setCode(403);
            return result;
        }
        teacher.setLevel(newLevel);
        teacher.setUserId(userId);

        int i = teacherMapper.updateTeacher(teacher);
        if(i==1){
            result.setMsg("升级完成");
            result.setCode(200);
            result.setData(i);
        }else {
            result.setMsg("升级失败");
            result.setCode(403);
            result.setData(i);
        }

        return result;
    }

    @Override
    public Result demoteTeacher(TeacherDto teacherDto) {
        Integer level = teacherDto.getLevel();
        String userId = teacherDto.getUserId();
        Teacher teacher = new Teacher();
        Result result =new Result();
        Integer newLevel = level -1;
        if(newLevel < 0){
            result.setMsg("已经是最低级");
            result.setCode(403);
            return result;
        }
        teacher.setLevel(newLevel);
        teacher.setUserId(userId);
        int i = teacherMapper.updateTeacher(teacher);
        if(i==1){
            result.setMsg("降级完成");
            result.setCode(200);
            result.setData(i);
        }else {
            result.setMsg("降级失败");
            result.setCode(403);
            result.setData(i);
        }
        return result;
    }


}
