package com.example.model.vo;

import com.example.model.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class TeacherVo extends Teacher {
    private Integer countAppointment;
    private String teacherFreeTime;
    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县（区）
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;
}
