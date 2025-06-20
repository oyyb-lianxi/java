package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class Notification extends BasePojo{
    private String id;
    private String teacherId;
    private String studentId;
    private String message;
}
