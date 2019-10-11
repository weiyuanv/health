package com.wei.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wei.api.CheckGroupService;
import com.wei.api.OrderSettingService;
import com.wei.common.MessageConstant;
import com.wei.common.POIUtils;
import com.wei.entity.PageResult;
import com.wei.entity.QueryPageBean;
import com.wei.entity.Result;
import com.wei.pojo.CheckGroup;
import com.wei.pojo.OrderSetting;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/7 16:09
 * @description:
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log = Logger.getLogger(OrderSettingController.class);
    @Reference
    private OrderSettingService orderSettingService;

    //上传文件，写入数据库
    @RequestMapping("/upload")
    public Result add(@RequestParam("excelFile") MultipartFile file) {
        try {
            //读取excel表格
            List<String[]> strings = POIUtils.readExcel(file);
            if (strings != null) {
                //用来存放OrderSetting
                List<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] string : strings) {
                    OrderSetting orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(new Date(string[0]));
                    orderSetting.setNumber(Integer.parseInt(string[1]));
                    orderSettings.add(orderSetting);
                    orderSettingService.importOrderSettings(orderSettings);
                }
            }
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            log.error("Upload error.",e);
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    //查询当月预约数据
    @RequestMapping("/findOrderSettingByMonth")
    public Result findOrderSettingByMonth(@RequestParam("date") String date) {

        try {
            List<Map> list = orderSettingService.findOrderSettingByMonth(date);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        } catch (Exception e) {
            log.error("findOrderSettingByMonth error.",e);
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    //更新数据
    @RequestMapping("/updateNum")
    public Result updateNum(@RequestBody OrderSetting orderSetting) {

        try {
            orderSettingService.updateNum(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("updateNum error.",e);
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
