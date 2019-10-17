package com.wei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wei.api.UserService;
import com.wei.dao.UserDao;
import com.wei.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/17 15:34
 * @description:
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserName(String username) {

        User user = userDao.findByUserName(username);
        return user;
    }
}
