package com.example.service;

import com.example.model.domain.User;

public interface UserService {

    public Boolean saveUser(User user);

    public Boolean updateById(User user);

    public Boolean deleteById(User user);

    public User getByOpenId(String openid);
}
