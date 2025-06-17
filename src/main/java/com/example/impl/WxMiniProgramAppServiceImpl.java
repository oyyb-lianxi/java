//package com.example.helloworld.impl;
//
//import com.example.helloworld.service.WxMiniProgramAppService;
//import com.example.helloworld.service.WxMiniProgramInterfaceProxyService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClientException;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
///***
// * @author qingfeng.zhao
// * @date 2023/5/19
// * @apiNote
// */
//@Slf4j
//@Service
//public class WxMiniProgramAppServiceImpl implements WxMiniProgramAppService {
//
//    final WxMiniProgramInterfaceProxyService wxMiniProgramInterfaceProxyService;
//    final WxMiniProgramCustomerJpaRepository wxMiniProgramCustomerJpaRepository;
//
//    public WxMiniProgramAppServiceImpl(WxMiniProgramInterfaceProxyService wxMiniProgramInterfaceProxyService, WxMiniProgramCustomerJpaRepository wxMiniProgramCustomerJpaRepository) {
//        this.wxMiniProgramInterfaceProxyService = wxMiniProgramInterfaceProxyService;
//        this.wxMiniProgramCustomerJpaRepository = wxMiniProgramCustomerJpaRepository;
//    }
//
//    /**
//     *
//     * @param code 微信小程序通过 wx.login() 方法获取到的 code
//     * @return 返回用户登录成功的 token
//     */
//    @Override
//    public VueElementAdminResponseVO wxMiniProgramLogin(String code) {
//        log.info("微信小程序登录授权请求参数:{}",code);
//        // 微信小程序授权登录参数校验
//        VueElementAdminResponseVO vueElementAdminResponseVO=checkWxMiniProgramLoginParam(code);
//        String trackId=vueElementAdminResponseVO.getTrackId();
//        try {
//            // 参数校验未通过则直接返回结果
//            if(!vueElementAdminResponseVO.getCode().equals(ResponseCodeEnum.OK_SUCCESS.getCode())){
//                return vueElementAdminResponseVO;
//            }
//            // 调用微信小程序登录功能
//            WxMiniProgramLoginDTO wxMiniProgramLoginDTO=wxMiniProgramInterfaceProxyService.code2Session(code);
//            log.info("开发者服务器-调用-微信接口服务-登录接口-返回结果:{}",wxMiniProgramLoginDTO);
//            if(null==wxMiniProgramLoginDTO|| null==SmartStringUtils.trimToNull(wxMiniProgramLoginDTO.getOpenid())){
//                vueElementAdminResponseVO.setCode(40001);
//                vueElementAdminResponseVO.setMessage("微信小程序微信授权登陆失败");
//                vueElementAdminResponseVO.setData(null);
//                return vueElementAdminResponseVO;
//            }
//            // 微信小程序登录返回数据对象
//            WxMiniProgramLoginVO wxMiniProgramLoginVO=new WxMiniProgramLoginVO();
//
//            WxUserBaseInfoEntity savedWxUserBaseInfoEntity;
//            Optional<WxUserBaseInfoEntity> wxMiniProgramCustomerEntityOptional=wxMiniProgramCustomerJpaRepository.findByOpenId(wxMiniProgramLoginDTO.getOpenid());
//            if(wxMiniProgramCustomerEntityOptional.isPresent()){
//                WxUserBaseInfoEntity wxUserBaseInfoEntity =wxMiniProgramCustomerEntityOptional.get();
//                wxUserBaseInfoEntity.setUpdateTime(new Date());
//                wxUserBaseInfoEntity.setToken(SmartStringUtils.generateSnowFakeIdStr());
//                savedWxUserBaseInfoEntity =wxMiniProgramCustomerJpaRepository.save(wxUserBaseInfoEntity);
//            }else{
//                WxUserBaseInfoEntity wxUserBaseInfoEntity =new WxUserBaseInfoEntity();
//                wxUserBaseInfoEntity.setUnionId(wxMiniProgramLoginDTO.getUnionid());
//                wxUserBaseInfoEntity.setOpenId(wxMiniProgramLoginDTO.getOpenid());
//                wxUserBaseInfoEntity.setNickName(SmartStringUtils.getCustomizedDigitsUuid(8));
//
//                wxUserBaseInfoEntity.setSessionKey(wxMiniProgramLoginDTO.getSession_key());
//                wxUserBaseInfoEntity.setToken(SmartStringUtils.generateSnowFakeIdStr());
//
//                wxUserBaseInfoEntity.setStatus(true);
//                wxUserBaseInfoEntity.setCreateTime(new Date());
//                wxUserBaseInfoEntity.setUpdateTime(new Date());
//
//                savedWxUserBaseInfoEntity =wxMiniProgramCustomerJpaRepository.save(wxUserBaseInfoEntity);
//            }
//            // 微信小程序授权登录 token
//            wxMiniProgramLoginVO.setToken(savedWxUserBaseInfoEntity.getToken());
//            vueElementAdminResponseVO.setCode(20000);
//            vueElementAdminResponseVO.setMessage("微信小程序微信授权登陆成功");
//            vueElementAdminResponseVO.setData(wxMiniProgramLoginVO);
//        } catch (RestClientException e) {
//            log.error("trackId:{},微信小程序微信授权登陆请求参数code:{},微信小程序微信授权登陆请求成功异常",trackId,code,e);
//            vueElementAdminResponseVO.setCode(50000);
//            vueElementAdminResponseVO.setMessage("微信小程序微信授权登陆请求成功异常");
//            vueElementAdminResponseVO.setData(null);
//        }
//        return vueElementAdminResponseVO;
//    }
//
//
//    @Override
//    public VueElementAdminResponseVO checkWxMiniProgramLoginParam(String code) {
//        VueElementAdminResponseVO vueElementAdminResponseVO=new VueElementAdminResponseVO();
//        try {
//            if(null==SmartStringUtils.trimToNull(code)){
//                vueElementAdminResponseVO.setCode(ResponseCodeEnum.REQUEST_PARAM_ERROR.getCode());
//                vueElementAdminResponseVO.setMessage("登录时获取的code不可为空,可通过wx.login获取");
//                vueElementAdminResponseVO.setData(null);
//            }
//            vueElementAdminResponseVO.setCode(ResponseCodeEnum.OK_SUCCESS.getCode());
//            vueElementAdminResponseVO.setMessage("微信小程序登陆参数校验通过");
//            vueElementAdminResponseVO.setData(null);
//        } catch (Exception e) {
//            log.error("trackId:{},微信小程序登陆参数校验异常,异常详情:",vueElementAdminResponseVO.getTrackId(),e);
//            vueElementAdminResponseVO.setCode(ResponseCodeEnum.REQUEST_ERROR.getCode());
//            vueElementAdminResponseVO.setMessage("微信小程序登陆参数校验异常");
//            vueElementAdminResponseVO.setData(null);
//        }
//        return vueElementAdminResponseVO;
//    }
//
//
//
//    @Override
//    public VueElementAdminResponseVO checkWxMiniProgramLogin(String token) {
//        VueElementAdminResponseVO vueElementAdminResponseVO=new VueElementAdminResponseVO();
//        try {
//            Optional<WxUserBaseInfoEntity> wxMiniProgramCustomerEntityOptional=this.wxMiniProgramCustomerJpaRepository.findByToken(token);
//            if(wxMiniProgramCustomerEntityOptional.isPresent()){
//                Map<String,Object> resultMap=new HashMap<>();
//                resultMap.put("haveLoginFlag",true);
//                vueElementAdminResponseVO.setCode(20000);
//                vueElementAdminResponseVO.setMessage("用户已登录");
//                vueElementAdminResponseVO. setData(resultMap);
//            }else{
//                Map<String,Object> resultMap=new HashMap<>();
//                resultMap.put("haveLoginFlag",false);
//                vueElementAdminResponseVO.setCode(40001);
//                vueElementAdminResponseVO.setMessage("token已失效,请重新登录");
//                vueElementAdminResponseVO. setData(resultMap);
//            }
//        } catch (Exception e) {
//            log.error("trackId:{},检查用户是否已登录异常,校验token:{},异常详情:",vueElementAdminResponseVO.getTrackId(),token,e);
//            vueElementAdminResponseVO.setCode(50000);
//            vueElementAdminResponseVO.setMessage("检查用户是否已登录异常");
//            vueElementAdminResponseVO. setData(null);
//        }
//        return vueElementAdminResponseVO;
//    }
//
//    @Override
//    public VueElementAdminResponseVO fetchWxUserInfo(String token) {
//        VueElementAdminResponseVO vueElementAdminResponseVO=new VueElementAdminResponseVO();
//        Optional<WxUserBaseInfoEntity> wxMiniProgramCustomerEntityOptional=this.wxMiniProgramCustomerJpaRepository.findByToken(token);
//        if(wxMiniProgramCustomerEntityOptional.isPresent()){
//            WxUserBaseInfoEntity wxUserBaseInfoEntity =wxMiniProgramCustomerEntityOptional.get();
//            WxUserInfoVO wxMiniProgramLoginVO=convertToWxMiniProgramCustomerVO(wxUserBaseInfoEntity);
//            vueElementAdminResponseVO.setCode(20000);
//            vueElementAdminResponseVO.setMessage("获取用户信息成功");
//            vueElementAdminResponseVO.setData(wxMiniProgramLoginVO);
//        }else{
//            vueElementAdminResponseVO.setCode(40001);
//            vueElementAdminResponseVO.setMessage("无效的用户登录token");
//            vueElementAdminResponseVO.setData(null);
//        }
//        return vueElementAdminResponseVO;
//    }
//
//    @Override
//    public WxUserInfoVO convertToWxMiniProgramCustomerVO(WxUserBaseInfoEntity wxUserBaseInfoEntity) {
//        WxUserInfoVO wxUserInfoVO =new WxUserInfoVO();
//        BeanUtils.copyProperties(wxUserBaseInfoEntity, wxUserInfoVO);
//        wxUserInfoVO.setId(String.valueOf(wxUserBaseInfoEntity.getId()));
//        String createDateTimeStr = DateUtil.format(wxUserBaseInfoEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
//        wxUserInfoVO.setCreateDateTime(createDateTimeStr);
//        String updateDateTimeStr = DateUtil.format(wxUserBaseInfoEntity.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
//        wxUserInfoVO.setUpdateDateTime(updateDateTimeStr);
//        return wxUserInfoVO;
//    }
//}
