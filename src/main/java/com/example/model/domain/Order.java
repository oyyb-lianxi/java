package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BasePojo{
    private String orderId;
    private String studentId;
    private String teacherId;
    private LocalDateTime orderTime;
    private LocalDateTime orderDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private BigDecimal amount;

}
