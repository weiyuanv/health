package com.wei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wei.api.OrderSettingService;
import com.wei.dao.OrderSettingDao;
import com.wei.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/11 15:53
 * @description:
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;


    //上传文件，写入数据库
    @Override
    public void importOrderSettings(List<OrderSetting> orderSettings) {
        //首先查询是否插入过值
        for (OrderSetting orderSetting : orderSettings) {
            Integer count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if (count > 0) {
                //如果设置过值就更新
                orderSettingDao.updateNumber(orderSetting);
            } else {
                //没有设置过就添加
                orderSettingDao.add(orderSetting);
            }
        }
    }

    //查询当月预约数据
    @Override
    public List<Map> findOrderSettingByMonth(String date) {

        //开始查询日期,可以防止把不同月份的相同天混淆
        String startDate = date + "-1";
        String endDate = date + "-31";
        List<OrderSetting> mapList = orderSettingDao.findOrderSettingByMonth(startDate,endDate);

        //用于存最终结果的list
        List<Map> arrayList = new ArrayList<>();
        for (OrderSetting map : mapList) {
            Map<String, Integer> hashMap = new HashMap<>();
            hashMap.put("date", map.getOrderDate().getDate());
            hashMap.put("number", map.getNumber());
            hashMap.put("reservations",map.getReservations());
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    //更新数据
    @Override
    public void updateNum(OrderSetting orderSetting) {
        //首先查询是否插入过值

        Integer count = (Integer) orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());

            if (count > 0) {
                //如果设置过值就更新
                orderSettingDao.editNumberByOrderDate(orderSetting);
            } else {
                //没有设置过就添加
                orderSettingDao.add(orderSetting);
            }


    }
}
