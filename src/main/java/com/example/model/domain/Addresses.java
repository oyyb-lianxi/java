package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//后台系统的管理员对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Addresses  extends BasePojo {
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

}





// CREATE TABLE addresses (
//     address_id INT AUTO_INCREMENT PRIMARY KEY,
//     user_id INT NOT NULL,
//     user_type ENUM('student', 'teacher') NOT NULL,
//     province VARCHAR(50) NOT NULL,
//     city VARCHAR(50) NOT NULL,
//     district VARCHAR(50) NOT NULL,
//     detailed_address TEXT,
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//     FOREIGN KEY (user_id) REFERENCES students(student_id) ON DELETE CASCADE,
//     FOREIGN KEY (user_id) REFERENCES teachers(teacher_id) ON DELETE CASCADE
// );
