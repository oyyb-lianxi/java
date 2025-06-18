package com.example.mapper;

import com.example.model.domain.Appointment;
import com.example.model.domain.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentMapper {
  
    void saveAppointment(Appointment appointment);

    Appointment getAppointmentById(Long id);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByConditions(Map<String, Object> params);

    void updateAppointment(Appointment appointment);

    void deleteAppointmentById(Long id);

    void saveNotification(Notification notification);
}
