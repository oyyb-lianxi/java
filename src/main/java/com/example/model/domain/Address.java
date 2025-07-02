package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//后台系统的管理员对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address  extends BasePojo {
    /**
     * id
     */
    private Long id;
    /**
     * 用户openID
     */
    private String userId;
    /**
     * 用户类型
     */
    private String userType;
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