package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class Teacher extends BasePojo{
    private Long id;
    private String userId;
    private String name;
    private String vxName;
    private String gender; //性别 man woman
    private String primarySchool;
    private String middleSchool;
    private String highSchool;
    private String university;
    private String chinaIdNumber;
    private String personalizedSignature;//个性签名
    private String address;//授课区域
    private String mobile;
    private Integer teachingSeniority; //教龄
    private String teachingType;
    private String personalProfile;  //个人简介;
    private String academicQualification; //学历
    private String myStrengths; //我的优势myStrengths
    private Integer price; //价格
    private Integer level; //等级
    private String lifePicture; //头像

}
