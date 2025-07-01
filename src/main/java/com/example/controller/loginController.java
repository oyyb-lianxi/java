package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.model.domain.User;
import com.example.model.dto.TeacherDto;
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
    public ResponseEntity saveTeacherInfo(@RequestBody Teacher teacherDto){
        //3、调用service
        System.out.println("saveUserInfo ==>"+teacherDto);
        Boolean aBoolean = teacherInfoService.saveTeacher(teacherDto);
        if(aBoolean){
            return  ResponseEntity.ok(teacherDto);
        }
        return ResponseEntity.ok(aBoolean);
    }
    @PostMapping("/saveStudentInfo")
    public ResponseEntity saveStudentInfo(@RequestBody Student student){
        //3、调用service
        System.out.println("saveUserInfo ==>"+student);
        Boolean aBoolean = studentService.saveStudent(student);
        if(aBoolean){
            return  ResponseEntity.ok(student);
        }
        return ResponseEntity.ok(aBoolean);
    }
   @PostMapping("/customerCheckSessionKey")
   public String getWxInfoTest(@RequestBody Admin admin) {
       admin.get
       log.info("微信的返回值{}", wxJson);
       return wxJson.toJSONString();
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
