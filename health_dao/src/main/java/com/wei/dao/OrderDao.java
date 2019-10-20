package com.wei.dao;

import com.wei.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 17:31
 * @description:
 */
public interface OrderDao {
    Order findByCondition(Order order);

    void add(Order order);

    Map findById(Integer id);

    List<Map<String, Object>> getSetmealReport();

    //今日预约数
    Integer findTodayOrderNumber(@Param("today") String today);

    //本周或本月预约数
    Integer findOrderNumberAfterDate(@Param("date")String date);

    //今日到诊数
    Integer findTodayVisitsNumber(@Param("today")String today);

    //本周或本月到诊数
    Integer findVisitsNumberAfterDate(@Param("date")String date);

    //热门套餐
    List<Map<String,Object>> findHotSetmeal();

}
