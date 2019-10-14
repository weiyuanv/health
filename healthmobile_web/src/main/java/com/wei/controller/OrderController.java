package com.wei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.wei.api.OrderService;
import com.wei.common.MessageConstant;
import com.wei.common.RedisMessageConstant;
import com.wei.common.SMSUtils;
import com.wei.common.ValidateCodeUtils;
import com.wei.entity.Result;
import com.wei.pojo.Order;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 15:01
 * @description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger log = Logger.getLogger(OrderController.class);

    @Reference
    private OrderService orderService;

    @Autowired
    JedisPool jedisPool;

    //提交预约订单
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //1.校验验证码
        //获取手机号
        String telephone = (String) map.get("telephone");
        //获取redis中的验证码
        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + telephone);
        //获取前端传来的验证码
        String validateCode = (String) map.get("validateCode");
        if (codeInRedis == null || !(codeInRedis.equals(codeInRedis))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Result result =null;
        //2.调用预约服务
        //设置预约类型
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        //3.返回结果
        if (result.isFlag()) {
            //预约成功发送短信，短信附加几号就诊
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //预约成功数据显示
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}




