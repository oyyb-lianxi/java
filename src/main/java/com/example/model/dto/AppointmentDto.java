package com.example.model.dto;

import com.example.model.domain.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class AppointmentDto {

    private String appointmentDateDto;

    private String appointmentStartTimeDto;

    private String appointmentEndTimeDto;

    private Long id;
    private String studentId;
    private String teacherId;
    private String adminPhone;
    private String subject;
    private String status; // 例如：PENDING, CONFIRMED, CANCELLED
    private String location;
    private String comments;
    private int duration;
}
