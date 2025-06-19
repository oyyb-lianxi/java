package com.example.service;

import com.example.model.domain.Appointment;
import com.example.model.vo.TimeSlotVo;
import com.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TimeSlotService {
    @Autowired AppointmentService appointmentService;
    public List<TimeSlotVo> getFreeTimeSlotsByTeacherId(Long teacherId, LocalDateTime startDate, LocalDateTime endDate, int slotDuration) {

        List<Appointment> appointments = appointmentService.getTeachersAppointments(teacherId);

        // 按时间排序预约记录
        appointments.sort((a1, a2) -> a1.getAppointmentDate().compareTo(a2.getAppointmentDate()));

        List<TimeSlotVo> freeTimeSlots = new ArrayList<>();

        LocalDateTime current = startDate;
        while (current.isBefore(endDate)) {
            boolean isFree = true;
            for (Appointment appointment : appointments) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime appointmentStart = LocalDateTime.parse(appointment.getAppointmentDate(), formatter);
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
