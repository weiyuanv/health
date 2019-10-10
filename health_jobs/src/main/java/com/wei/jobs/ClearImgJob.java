package com.wei.jobs;

import com.wei.common.QiniuUtils;
import com.wei.common.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/10 20:50
 * @description:
 */
@Component
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        //对redis中的存储图片的两个set集合进行比较，获取垃圾图片名称
        Set<String> imgSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //遍历差值，获取其中的图片名称
        for (String s : imgSet) {
            //删除七牛云中的图片
            QiniuUtils.deleteFileFromQiniu(s);
            //删除redis中多余的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);

        }

    }
}
