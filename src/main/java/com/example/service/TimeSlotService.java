package com.example.service;

import com.example.model.domain.Appointment;
import com.example.model.dto.AppointmentDto;
import com.example.model.vo.AppointmentVo;
import com.example.model.vo.TimeSlotVo;
import com.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TimeSlotService {
    @Autowired AppointmentService appointmentService;

    /**
     * 寻找某个时间段有空的老师
     * @param teacherAppointment
     * @param startDate
     * @param endDate
     * @param slotDuration
     * @return
     */
    public List<TimeSlotVo> getFreeTimeSlotsByTeacherId(AppointmentDto teacherAppointment, String appointmentDate, String startDate, String endDate, int slotDuration) {

        //查询该老师的所有预约
        List<AppointmentVo> appointments = appointmentService.getAppointmentsByConditions(teacherAppointment);

        // 按时间排序预约记录
        appointments.sort((a1, a2) -> a1.getAppointmentDate().compareTo(a2.getAppointmentDate()));

        List<TimeSlotVo> freeTimeSlots = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime appointmentStartTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime appointmentEndTime = LocalDateTime.parse(endDate, formatter);
        LocalDateTime current = appointmentStartTime;
        while (current.isBefore(appointmentEndTime)) {
            boolean isFree = true;
            for (AppointmentVo appointment : appointments) {
                LocalDateTime appointmentStart =new Timestamp(appointment.getAppointmentStartTime()).toLocalDateTime();
                LocalDateTime appointmentEnd = appointmentStart.plusMinutes(appointment.getDuration());

                if (current.isBefore(appointmentEnd) && current.plusMinutes(slotDuration).isAfter(appointmentStart)) {
                    current = appointmentEnd;
                    isFree = false;
                    break;
                }
            }

            if (isFree) {
                freeTimeSlots.add(new TimeSlotVo(current, current.plusMinutes(slotDuration)));
                current = current.plusMinutes(slotDuration);
            } else {
                current = current.plusMinutes(1); // 移动到下一个时间点
            }
        }

        return freeTimeSlots;
    }
}
