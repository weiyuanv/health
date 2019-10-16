package com.wei.controller;

import com.aliyuncs.exceptions.ClientException;
import com.wei.common.MessageConstant;
import com.wei.common.RedisMessageConstant;
import com.wei.common.SMSUtils;
import com.wei.common.ValidateCodeUtils;
import com.wei.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 15:01
 * @description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private static final Logger log = Logger.getLogger(ValidateCodeController.class);

    @Autowired
    JedisPool jedisPool;

    //发送验证码
    @RequestMapping("/send4Order")
    public Result sendMessage(String telephone) {
        //产生4位验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        try {
            //发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code+"");

        } catch (ClientException e) {
            log.error("SendMessage Error.", e);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //将短信存入redis
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER + telephone,5*60*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


    //发送登录验证码
    @RequestMapping("/send4Login")
    public Result sendMessageLogin(String telephone) {
        //产生4位验证码  6213
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        try {
            //发送短信
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code+"");

        } catch (Exception e) {
            log.error("SendMessageLogin Error.", e);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //将短信存入redis send4Login
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN + telephone,5*60*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
