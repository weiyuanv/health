package com.wei.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.wei.api.CheckGroupService;
import com.wei.api.SetmealService;
import com.wei.common.MessageConstant;
import com.wei.common.QiniuUtils;
import com.wei.common.RedisConstant;
import com.wei.entity.PageResult;
import com.wei.entity.QueryPageBean;
import com.wei.entity.Result;
import com.wei.pojo.CheckGroup;
import com.wei.pojo.Setmeal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/7 16:09
 * @description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static final Logger log = Logger.getLogger(SetmealController.class);
    @Reference
    private CheckGroupService checkGroupService;

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;


    //新增数据
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {

        try {
            //获取文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf);

            //随机产生文件名，防止覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            //上传七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);

            //将自动上传的图片保存到redis的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);


            //上传成功
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);

        } catch (IOException e) {
            e.printStackTrace();//todo 写法
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);

        }
    }
    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(Integer id) {

        try {
            List<CheckGroup> list = checkGroupService.findAll();

            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            log.error("finAll error.", e);
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //新增数据  setmeal--套餐   checkgroupIds--检查组的id
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        try {
            setmealService.add(setmeal, checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            log.error("Add SETMEAL error.",e);
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
        } catch (Exception e) {
            log.error("QUERY SETMEAL FAIL.",e);
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


}
