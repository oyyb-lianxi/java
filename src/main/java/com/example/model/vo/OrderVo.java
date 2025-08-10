package com.example.model.vo;

import com.example.model.domain.Order;
import com.example.model.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class OrderVo extends Order {

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 老师姓名
     */
    private String teacherName;

    /**
     * 订单状态描述
     */
    private String statusDesc;

}
