package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public int register(User user) {

        // 设置默认值
        if (user.getBalance() == null) {
            user.setBalance(new java.math.BigDecimal("0.00"));
        }
        if (null == user.getOpenId()||"".equals(user.getOpenId())) {
            user.setOpenId(UUID.randomUUID().toString());
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        // 插入新用户
        int res = userMapper.insert(user);
        return res;
    }

    @Override
    public User findByCarNumber(String carNumber) {
        return userMapper.findByCarNumber(carNumber);
    }
}
