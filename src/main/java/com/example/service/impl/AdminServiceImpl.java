package com.example.service.impl;

import com.example.mapper.AppointmentMapper;
import com.example.model.dto.AppointmentDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import com.example.service.AdminService;
import com.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Result adminQueryStudentToDo(AppointmentDto studentAppointment) {

        String appointmentDateDto = studentAppointment.getAppointmentDateDto();
        String appointmentCreatedDto = studentAppointment.getAppointmentCreatedDto();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (studentAppointment.getPage() != null && studentAppointment.getPageSize() != null) {
            studentAppointment.setOffSet((studentAppointment.getPage() - 1) * studentAppointment.getPageSize());
        }
        if (appointmentDateDto != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentDateDto, formatterDate);
            studentAppointment.setAppointmentDate(localDateTime);
        }
        if (appointmentCreatedDto != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(appointmentCreatedDto, formatterDate);
            studentAppointment.setCreated(localDateTime);
        }

        Result result = new Result();
        List<AppointmentVo> appointments = appointmentService.getAppointmentsByAdmin(studentAppointment);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        int countCancel = 0;
        //取消已经过期预约
        for (AppointmentVo appointment1 : appointments) {
            if (new Timestamp(appointment1.getAppointmentEndTime()).before(currentTimestamp) &&
                    appointment1.getStatus().equals("PENDING")) {
                appointmentService.cancelAppointment(appointment1.getId());
                countCancel++;
            }
        }
        result.setCode(200);
        Map<String,Object>map=new HashMap<>();
        if (countCancel > 0) {
            List<AppointmentVo> newAppointments = appointmentService.getAppointmentsByAdmin(studentAppointment);
            map.put("appointments",newAppointments);
        } else {
            map.put("appointments",appointments);
        }
        Integer integer = appointmentMapper.countAppointmentsByAdmin(studentAppointment);
        map.put("total",integer);
        result.setData(map);
        return result;
    }
}
