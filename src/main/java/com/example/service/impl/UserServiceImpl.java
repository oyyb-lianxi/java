package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.domain.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Boolean saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        int i = userMapper.saveUser(user);
        if(i==1){
            return true;
        }
        return false;
    }


    @Override
    public Boolean updateById(User user) {
        int i = userMapper.updateById(user);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteById(User user) {
        int i = userMapper.deleteById(user);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public User getByOpenId(String openid) {
        log.info("openid"+openid);
       return userMapper.getById(openid);

    }


}
