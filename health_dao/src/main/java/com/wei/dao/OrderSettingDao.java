package com.wei.dao;

import com.wei.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/11 16:04
 * @description:
 */
public interface OrderSettingDao {
    Integer findCountByOrderDate(Date orderDate);

    void updateNumber(OrderSetting orderSetting);

    void add(OrderSetting orderDate);

    List<OrderSetting> findOrderSettingByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    //手机预约查询该日是否可以预约
    OrderSetting findCountByDate(Date date);

    void updateReservationsByOrderDate(Date date);
}
