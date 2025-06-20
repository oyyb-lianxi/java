package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class Student extends BasePojo{
    private String id;
    private String userId;
    private String name;
    private String vxName;
    private String gender;
    //    private Date created;
//    private Date updated;
    private String primarySchool;
    private String middleSchool;
    private String highSchool;
    private String chinaIdNumber;
    private String address;
    private String mobile;
    private String academicQualification; //学历
}
