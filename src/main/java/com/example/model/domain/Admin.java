package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//后台系统的管理员对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String phone;
    
    /**
     * 管理员的角色权限级别
     */
    private String role;
    /**
     *  管理员的头像路径或URL
     */
    private String avatar ;
}
