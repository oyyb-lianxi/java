package com.example.controller;

import com.example.mapper.UserMapper;
import com.example.model.CommonUtils.JwtUtils;
import com.example.model.domain.Admin;
import com.example.model.domain.User;
import com.example.model.dto.StudentDto;
import com.example.model.dto.TeacherDto;
import com.example.model.entity.Result;
import com.example.service.*;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    private WechatToolsService wechatToolsService;
    @Autowired
    HttpUtils httpsUtils;
    @Autowired
    TeacherInfoService teacherInfoService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserMapper userMapper;
    /**
     * 校验登录
     */
    @GetMapping("/checkSessionKey/{code}")
    public ResponseEntity checkSessionKey(@PathVariable String code) throws IOException {
        Map<String, Object> result = new HashMap<>();
        String response = wechatToolsService.getOpenid(code);
        ObjectMapper objectMapper = new ObjectMapper();
        // 将 JSON 字符串转换为 Map
        Map<String, Object> map = objectMapper.readValue(response, Map.class);
        String openid = (String)map.get("openid");
        User user = userService.getByOpenId(openid);
        if(user == null){
            result.put("openid",openid);
            result.put("status","NEW");
            return ResponseEntity.ok(result);
        }
        System.out.println("hello openid ==>"+openid);
        //登录成功, 生成token
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("openid", openid);
        String token = JwtUtils.getToken(tokenMap);
        //5. 封装数据返回
        result.put("token", token);
        result.put("openid", openid);
        result.put("status", "OLD");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveUserInfo")
    public ResponseEntity saveUser(@RequestBody User user){
        Boolean aBoolean = userService.saveUser(user);
        return ResponseEntity.ok(aBoolean);
    }
    /**
     * 保存老师信息
     *   saveTeacherInfo
     *   请求头中携带token
     */
    @PostMapping("/saveTeacherInfo")
    public Result saveTeacherInfo(@RequestBody TeacherDto teacherDto){
        Result result =new Result();
        System.out.println("saveUserInfo ==>"+teacherDto);
        Boolean aBoolean = teacherInfoService.saveTeacher(teacherDto);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("保存成功");
            result.setData(teacherDto);
        }else {
            result.setMsg("保存失败");
        }
        return result;
    }
    @PostMapping("/saveStudentInfo")
    public Result saveStudentInfo(@RequestBody StudentDto student){
        Result result =new Result();
        System.out.println("saveUserInfo ==>"+student);
        Boolean aBoolean = studentService.saveStudent(student);
        result.setCode(200);
        if(aBoolean){
            result.setMsg("保存成功");
            result.setData(student);
        }else {
            result.setMsg("保存失败");
        }
        return result;

    }
   @PostMapping("/customerCheckSessionKey")
   public Result customerCheckSessionKey(@RequestBody Admin adminDto) {

       Result result =new Result();
       result.setCode(200);
       Map<String, Object> resultMap = new HashMap<>();
        String phone =  adminDto.getPhone();
       String password =  adminDto.getPassword();
       Admin  admin = userMapper.selectAdmin(phone,password);
        if(admin == null){
            result.setMsg("用户名密码错误");
            return result;
        }
               //登录成功, 生成token
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("openid", phone);
        String token = JwtUtils.getToken(tokenMap);
               //5. 封装数据返回
       resultMap.put("token", token);
       resultMap.put("openid", phone);
       resultMap.put("status", "OLD");
       result.setData(resultMap);
        return result;
   }
//    @PostMapping("/getWxInfoTest")
//    public String getWxInfoTest(@RequestBody JSONObject obj) {
//        String AppId = AppConfigTools.getWxAppId();
//        String AppSecret = AppConfigTools.getWxAppSecret();
//
//        JSONObject wxJson = JSONObject.parseObject(WechatTools.getOpenid(obj.getString("code"), AppId, AppSecret));
//        log.info("微信的返回值{}", wxJson);
//        return wxJson.toJSONString();
//    }
}
