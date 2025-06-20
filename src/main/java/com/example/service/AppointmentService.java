package com.example.service;

import com.example.model.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentService {

    public void makeAppointment(String studentId, String teacherId, String subject, String appointmentDate, String appointmentTime);
    public void confirmAppointment(String appointmentId);
    public void cancelAppointment(String appointmentId);
    public List<Appointment> getStudentsAppointments(String studentId);
    public List<Appointment> getTeachersAppointments(String teacherId);
    public List<Appointment> getAllAppointments();

}
