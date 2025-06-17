package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class Teacher {
    private Long id;
    private Long userId;
    private String name;
    private String vxName;
    private String gender; //性别 man woman
    private String primarySchool;
    private String middleSchool;
    private String highSchool;
    private String chinaIdNumber;
    private String chinaIdNumberPictureFileName;
    private String address;
    private String mobile;
    private Integer teachingSeniority; //教龄
    private String teachingType;
    private String personalProfile;  //个人简介;
    private String academicQualification; //学历
    private String academicQualificationPictureFileName; //学历照片
}
