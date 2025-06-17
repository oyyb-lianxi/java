//package com.example.helloworld.impl;
//
//import com.example.helloworld.service.WxMiniProgramInterfaceProxyService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Service;
//
///***
// * @author qingfeng.zhao
// * @date 2023/5/19
// * @apiNote
// */
//@Slf4j
//@Service
//public class WxMiniProgramInterfaceProxyServiceImpl implements WxMiniProgramInterfaceProxyService {
//
//    final HttpHeaders httpHeaders;
//    final WxMiniProgramProperties wxMiniProgramProperties;
//
//    public WxMiniProgramInterfaceProxyServiceImpl(HttpHeaders httpHeaders, WxMiniProgramProperties wxMiniProgramProperties) {
//        this.httpHeaders = httpHeaders;
//        this.wxMiniProgramProperties = wxMiniProgramProperties;
//    }
//
//    /**
//     * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html
//     * @param js_code 登录时获取的 code，可通过wx.login获取
//     * @return
//     */
//    @Override
//    public WxMiniProgramLoginDTO code2Session(String js_code) {
//        String apiUrl = "https://api.weixin.qq.com/sns/jscode2session" + "?appid=" +
//                wxMiniProgramProperties.getAppId() +
//                "&secret=" +
//                wxMiniProgramProperties.getAppSecret() +
//                "&js_code=" +
//                js_code +
//                "&grant_type=" +
//                "authorization_code";
//        RestTemplate restTemplate = new RestTemplate(RestTemplateUtils.createSecureTransport());
//        HttpEntity<String> entity = new HttpEntity<>(null,null);
//        ResponseEntity<String> response = restTemplate.exchange(
//                apiUrl,
//                HttpMethod.GET,
//                entity,
//                String.class
//        );
//        log.info("微信小程序授权登录返回结果:{}",response.getBody());
//        return SmartJackSonUtils.readValueToObject(response.getBody(), WxMiniProgramLoginDTO.class);
//    }
//}
