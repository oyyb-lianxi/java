package com.example.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentVo {
    private Long id;
    private String studentId;

    private String studentName;
    private String teacherId;

    private String teacherName;

    private String adminPhone;
    private String subject;
    private Long appointmentDate;

    private Long appointmentStartTime;

    private Long appointmentEndTime;
    private String status; // 例如：PENDING, CONFIRMED, CANCELLED
    private String location;
    private String comments;
    private int duration;
}
