package com.example.controller;

import com.example.model.domain.Teacher;
import com.example.model.domain.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    UserService userService;

    /**
     * 根据用户id查询老师信息
     */
    @GetMapping("/queryByUserId/{openid}")
    public ResponseEntity queryByUserId(@PathVariable String openid){
        User user = userService.getByOpenId(openid);
        return ResponseEntity.ok(user);
    }

    /**
     * 保存用户信息
     */
    @PostMapping("/saveUser")
    public ResponseEntity saveUser(@RequestBody User user){
        Boolean aBoolean = userService.saveUser(user);
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody User user){
        Boolean aBoolean = userService.updateById(user);
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 删除用户信息
     */
    @PostMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestBody User user){
        Boolean aBoolean = userService.deleteById(user);
        return ResponseEntity.ok(aBoolean);
    }

}
