package com.example.service.impl;

import com.example.service.HttpUtils;
import com.example.service.WechatToolsService;

public class WechatToolsServiceImpl   {
    public String getOpenid(String code) {
// 调用接口必要的参数
            StringBuilder data=new StringBuilder();
// appid、secret定义在配置文件中，注入到项目里
            data.append("appid="+"wx9a84c192fabfc317"+"&");
            data.append("secret="+ "50b111d93b4f19d272dde01f76507220"+"&");
            data.append("js_code="+ code+"&");
            data.append("grant_type="+ "authorization_code");

            String response = HttpUtils.getRequest("https://api.weixin.qq.com/sns/jscode2session?" + data);

            return response;
    }
}
