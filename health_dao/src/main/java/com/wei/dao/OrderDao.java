package com.wei.dao;

import com.wei.pojo.Order;

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
}
