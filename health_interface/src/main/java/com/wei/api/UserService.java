package com.wei.api;

import com.wei.pojo.User;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/17 15:24
 * @description:
 */
public interface UserService {
    User findByUserName(String username);
}
