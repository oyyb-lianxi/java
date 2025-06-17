package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.service.TeacherInfoService;
import com.example.service.WechatToolsService;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.service.HttpUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 校验登录
     */
    @GetMapping("/checkSessionKey/{code}")
    public ResponseEntity checkSessionKey(@PathVariable String code){
        String openid = wechatToolsService.getOpenid(code);
        //登录成功, 生成token
        Map<String, Object> tokenMap = new HashMap<String, Object>();

        tokenMap.put("openid", openid);
        String token = JwtUtils.getToken(tokenMap);
        System.out.println("hello openid ==>"+openid);
        //5. 封装数据返回
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveUserInfo")
    public String saveUserInfo(@RequestBody String userVxId){
        System.out.println("saveUserInfo ==>"+userVxId);
        return "hello ,springboot";
    }
    /**
     * 保存老师信息
     *   saveTeacherInfo
     *   请求头中携带token
     */
    @PostMapping("/saveTeacherInfo")
    public ResponseEntity saveTeacherInfo(@RequestBody Teacher teacherInfo
                                  ){
        //1、判断token是否合法
//        boolean verifyToken = JwtUtils.verifyToken(token);
//        if(!verifyToken) {
//            return ResponseEntity.status(401).body(null);
//        }
        //2、向userinfo中设置用户id
//        Claims claims = JwtUtils.getClaims(token);
//        Integer id = (Integer) claims.get("id");
        long uid = System.currentTimeMillis();

//        Long aLong = Long.valueOf(uuid.toString());
        teacherInfo.setId(uid);
        teacherInfo.setUserId(uid);
        //3、调用service
        System.out.println("saveUserInfo ==>"+teacherInfo);
        teacherInfoService.saveTeacher(teacherInfo);
        return ResponseEntity.ok(teacherInfo);
    }
    @PostMapping("/saveStudentInfo")
    public String saveStudentInfo(@RequestBody Student student){
        System.out.println("saveStudentInfo ==>"+student);
        return "hello ,student";
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
