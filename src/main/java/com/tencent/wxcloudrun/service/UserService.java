package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;

public interface UserService {
    int register(User user);

    User findByCarNumber(String carNumber);
}
