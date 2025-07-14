package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * 预约表
 */
@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class Appointment extends BasePojo{
    private Long id;
    private String studentId;
    private String studentName;
    private String teacherId;

    private String teacherName;

    private String adminPhone;
    private String subject;
    private LocalDateTime appointmentDate;

    private LocalDateTime appointmentStartTime;

    private LocalDateTime appointmentEndTime;
    private String status; // 例如：PENDING, CONFIRMED, CANCELLED
    private String location;
    private String comments;
    private int duration;

        // 重写toString方法
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + id +
                ", studentId=" + studentId +
                ", teacherId=" + teacherId +
                ", subject=" + subject +
                ", appointmentDate=" + appointmentDate +
                ", duration=" + duration +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
