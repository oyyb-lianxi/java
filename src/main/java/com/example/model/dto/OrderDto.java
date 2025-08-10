package com.example.model.dto;

import com.example.model.domain.Order;
import com.example.model.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class OrderDto extends Order {
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
