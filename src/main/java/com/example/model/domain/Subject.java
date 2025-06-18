package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class Subject {
    private Long id;
    private String name;
    private String description;
}
