package com.example.service;

import com.example.model.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentService {

    public void makeAppointment(Long studentId, Long teacherId, String subject, String appointmentDate, String appointmentTime);
    public void confirmAppointment(Long appointmentId);
    public void cancelAppointment(Long appointmentId);
    public List<Appointment> getStudentsAppointments(Long studentId);
    public List<Appointment> getTeachersAppointments(Long teacherId);
    public List<Appointment> getAllAppointments();

}
