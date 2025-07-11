package com.example.service;

import com.example.service.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class WechatToolsService  {

    public String getOpenid(String code){
// 调用接口必要的参数
        StringBuilder data=new StringBuilder();
// appid、secret定义在配置文件中，注入到项目里
        data.append("appid="+"wx981c08ff1599d3db"+"&");
        data.append("secret="+ "48dced9ca97e63700d2352f4935af512"+"&");
        data.append("js_code="+ code+"&");
        data.append("grant_type="+ "authorization_code");
        System.out.println(data);
        String response = HttpUtils.getRequest("https://api.weixin.qq.com/sns/jscode2session?" + data);
        return response;
    }


    public  static String checkSessionKey(String openid) {

        String response = HttpUtils.getRequest("https://api.weixin.qq.com/wxa/checksession?" +
                "access_token=ACCESS_TOKEN" +
                "&" +
                "signature=SIGNATURE" +
                "&" +
                "openid=OPENID" +
                "&" +
                "sig_method=SIG_METHOD");

        return response;
    }
}
