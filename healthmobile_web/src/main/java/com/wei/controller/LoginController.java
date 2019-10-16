package com.wei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wei.api.MemberService;
import com.wei.common.MessageConstant;
import com.wei.common.RedisMessageConstant;
import com.wei.entity.Result;
import com.wei.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/16 14:50
 * @description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        //获取手机号
        String telephone = (String) map.get("telephone");
        //获取redis验证码
        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + telephone);
        //获取前端传来的验证码
        String validateCode = (String) map.get("validateCode");

        //校验验证码
        if (codeInRedis == null || !(codeInRedis.equals(validateCode))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            //验证码正确、检验是否为会员
            Member member = memberService.findMemberByTelephone(telephone);
            if (member == null) {
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }

            //登录成功、设置cookie
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);

        }

    }
}
