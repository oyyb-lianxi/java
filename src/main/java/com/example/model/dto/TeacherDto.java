package com.example.model.dto;

import com.example.model.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor  //满参构造方法
@NoArgsConstructor  //无参构造方法
public class TeacherDto extends Teacher {
    private MultipartFile academicQualificationPictureFile;
    private MultipartFile chinaIdNumberPictureFile;
}
