package com.wei.dao;

import com.wei.pojo.User;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/17 15:35
 * @description:
 */
public interface UserDao {

    User findByUserName(String username);
}
