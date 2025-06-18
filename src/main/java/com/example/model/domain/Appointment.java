package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class Appointment {
    private Long id;
    private Long studentId;
    private Long teacherId;
    private String subject;
    private String appointmentDate;
    private String appointmentTime;
    private String status; // 例如：PENDING, CONFIRMED, CANCELLED
}
