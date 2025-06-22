package com.example.model.dto;

import com.example.model.domain.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class AppointmentDto extends Appointment {

    private String appointmentDateDto;

    private String appointmentStartTimeDto;

    private String appointmentEndTimeDto;
}
