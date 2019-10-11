package com.wei.api;

import com.wei.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/11 15:52
 * @description:
 *
 *              { date: 1, number: 120, reservations: 1 },
 *              { date: 3, number: 120, reservations: 1 },
 *              { date: 4, number: 120, reservations: 120 },
 *              { date: 6, number: 120, reservations: 1 },
 */
public interface OrderSettingService {
    void importOrderSettings(List<OrderSetting> orderSettings);


    List<Map> findOrderSettingByMonth(String date);

    void updateNum(OrderSetting orderSetting);
}
