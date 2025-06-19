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
    private String location;
    private String comments;
    private int duration;

        // 重写toString方法
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", studentId=" + studentId +
                ", teacherId=" + teacherId +
                ", courseId=" + courseId +
                ", appointmentDate=" + appointmentDate +
                ", duration=" + duration +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
