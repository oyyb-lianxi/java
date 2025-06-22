package com.example.mapper;

import com.example.model.domain.Appointment;
import com.example.model.domain.Notification;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentMapper {

    Appointment saveAppointment(Appointment appointment);

    Appointment getAppointmentById(Long id);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByConditions(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointmentById(String id);

    void saveNotification(Notification notification);

    boolean existsByAppointmentDate(String teacherId, String studentId, LocalDateTime appointmentDate,
                                    LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime);
}
