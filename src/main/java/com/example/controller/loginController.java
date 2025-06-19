package com.example.controller;

import com.example.model.CommonUtils.JwtUtils;
import com.example.model.domain.User;
import com.example.model.dto.TeacherDto;
import com.example.service.TeacherInfoService;
import com.example.service.UserService;
import com.example.service.WechatToolsService;
import com.example.model.domain.Student;
import com.example.model.domain.Teacher;
import com.example.service.HttpUtils;
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
        //1、判断token是否合法,
        //                                          @RequestHeader("Authorization") String token
//        boolean verifyToken = JwtUtils.verifyToken(token);
//        if(!verifyToken) {
//            return ResponseEntity.status(401).body(null);
//        }
        //2、向userinfo中设置用户id
//        Claims claims = JwtUtils.getClaims(token);
//        Integer id = (Integer) claims.get("id");
//        long uid = System.currentTimeMillis();

//        Long aLong = Long.valueOf(uuid.toString());

        String teacherId = UUID.randomUUID().toString();
        teacherDto.setId(teacherId);
        //3、调用service
        System.out.println("saveUserInfo ==>"+teacherDto);
        teacherInfoService.saveTeacher(teacherDto);
        return ResponseEntity.ok(teacherDto);
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
