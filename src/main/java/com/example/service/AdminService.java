package com.example.service;


import com.example.model.domain.Appointment;
import com.example.model.dto.AppointmentDto;
import com.example.model.entity.Result;
import com.example.model.vo.AppointmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface AdminService {

    Result adminQueryStudentToDo(AppointmentDto studentAppointment);
    Result cancelAppointment(AppointmentDto studentAppointment);
}