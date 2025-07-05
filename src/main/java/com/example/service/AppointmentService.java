package com.example.service;

import com.example.model.domain.Appointment;
import com.example.model.vo.AppointmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentService {

    public void makeAppointment(String studentId, String teacherId, String subject, LocalDateTime appointmentDate,
                                LocalDateTime appointmentStartTime,LocalDateTime appointmentEndTime);
    public Boolean confirmAppointment(Long appointmentId);
    public Boolean successFinishAppointment(Long appointmentId);
    public void cancelAppointment(Long appointmentId);
    public List<AppointmentVo> getAppointmentsByConditions(Appointment appointment);

    public Appointment getAppointmentsById(Long appointmentId);
    public List<Appointment> getAllAppointments();
    public Integer createAppointment(Appointment appointment);
    public boolean isTimeSlotAvailable(Appointment appointment);
}
