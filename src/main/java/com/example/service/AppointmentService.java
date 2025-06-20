package com.example.service;

import com.example.model.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentService {

    public void makeAppointment(String studentId, String teacherId, String subject, String appointmentDate, String appointmentStartTime,String appointmentEndTime);
    public void confirmAppointment(String appointmentId);
    public void cancelAppointment(String appointmentId);
    public List<Appointment> getAppointmentsByUserId(String userId);

    public Appointment getAppointmentsById(String appointmentId);
    public List<Appointment> getAllAppointments();
    public List<Appointment> getTeachersAppointments(String teacherId);
    public Appointment createAppointment(Appointment appointment);
    public boolean isTimeSlotAvailable(String teacherId,String studentId,String appointmentDate,String startTime,String endTime);
}
