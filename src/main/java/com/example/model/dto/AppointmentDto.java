package com.example.model.dto;

import com.example.model.domain.Appointment;
import com.example.model.domain.BasePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class AppointmentDto extends BasePojo {
    /**
     * 分页开始点
     */
    private Integer offSet;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 页码
     */
    private Integer page;
    private String appointmentDateDto;
    private String appointmentCreatedDto;

    private String appointmentStartTimeDto;

    private String appointmentEndTimeDto;
    private LocalDateTime appointmentDate;

    private LocalDateTime appointmentStartTime;

    private LocalDateTime appointmentEndTime;
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
