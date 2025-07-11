package com.example.mapper;

import com.example.model.domain.Appointment;
import com.example.model.domain.Notification;
import com.example.model.dto.AppointmentDto;
import com.example.model.vo.AppointmentVo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentMapper {

    @Select("SELECT count(*) FROM appointment where created >= CURDATE()")
    int countNewTodayAppointment();
    @Select("SELECT count(*) FROM appointment")
    int countAllAppointment();
    @Select("SELECT appointmentDate FROM appointment  " +
            "WHERE teacherId = #{teacherId} and appointmentDate BETWEEN #{startOfMonth} AND #{endOfMonth}" +
            "GROUP BY appointmentDate")
    List<String> queryTeacherMonthToDo(@Param("teacherId") String teacherId, @Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);
    
    int saveAppointment(Appointment appointment);

    Appointment getAppointmentById(Long id);

    List<Appointment> getAllAppointments();

    List<AppointmentVo> getAppointmentsByConditions(AppointmentDto appointment);
    List<AppointmentVo> getAppointmentsByAdmin(AppointmentDto appointment);
    Integer countAppointmentsByConditions(AppointmentDto appointment);
    Integer countAppointmentsByAdmin(AppointmentDto appointment);

    Integer getAppointmentByTeacherId(String teacherId);

    void updateAppointment(Appointment appointment);

    void deleteAppointmentById(String id);

    void saveNotification(Notification notification);

    boolean existsByAppointmentDate(Appointment appointment);
}
