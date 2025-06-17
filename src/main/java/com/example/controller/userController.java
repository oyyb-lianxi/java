package com.example.controller;

import com.example.model.domain.Teacher;
import com.example.model.domain.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    UserService userService;
    /**
     * 修改用户信息
     */
    @PostMapping("/updateById")
    public ResponseEntity updateById(@RequestBody User user){
        Boolean aBoolean = userService.updateById(user);
        return ResponseEntity.ok(aBoolean);
    }

    /**
     * 根据用户id查询老师信息
     */
    @GetMapping("/queryByUserId/{userId}")
    public ResponseEntity queryByUserId(@PathVariable Long userId){
        User user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }
}
