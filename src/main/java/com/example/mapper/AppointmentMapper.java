package com.example.mapper;

import com.example.model.domain.Appointment;
import com.example.model.domain.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentMapper {

    Appointment saveAppointment(Appointment appointment);

    Appointment getAppointmentById(String id);

    List<Appointment> getAppointmentByUserId(String id);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByConditions(Map<String, Object> params);

    void updateAppointment(Appointment appointment);

    void deleteAppointmentById(String id);

    void saveNotification(Notification notification);

    boolean existsByAppointmentDate(String appointmentDate, String appointmentStartTime, String appointmentEndTime);
}
