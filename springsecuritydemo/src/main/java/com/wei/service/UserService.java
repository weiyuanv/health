package com.wei.service;

import com.wei.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/16 16:44
 * @description:
 */
public class UserService implements UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //模拟数据库中的用户数据
    public  static Map<String, User> map = new HashMap<>();


    static {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("admin");

        User user2 = new User();
        user2.setUsername("test");
        user2.setPassword("test");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }

    public void initData() {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("admin"));

        User user2 = new User();
        user2.setUsername("test");
        user2.setPassword(passwordEncoder.encode("test"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        initData();

        System.out.println("username = " + username);
        User userInDb = map.get(username);
        if (userInDb == null) {
            return null;
        }

        /*//模拟数据库中的密码，后期需要查询数据库
        String passwordInDb = "{noop}" + userInDb.getPassword();*/

        //模拟数据库中的密码，后期需要查询数据库
        String passwordInDb = userInDb.getPassword();

        List<GrantedAuthority> list = new ArrayList<>();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, passwordInDb, list);
        return user;
    }



}
