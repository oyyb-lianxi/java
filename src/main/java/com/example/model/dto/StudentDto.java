package com.example.model.dto;

import com.example.model.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class StudentDto extends Student {
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
