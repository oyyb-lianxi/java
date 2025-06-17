package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Boolean saveUser(User user) {
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
    public User getById(Long userId) {
       return userMapper.getById(userId);

    }


}
