package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class TeacherTime extends BasePojo{
    private Long id;
    private String teacherId;
    private String startTime;
    private String endTime;
}
