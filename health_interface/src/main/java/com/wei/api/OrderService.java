package com.wei.api;

import com.wei.entity.Result;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 16:11
 * @description:
 */
public interface OrderService {
    Result order(Map map);

    Map findById(Integer id);

    //查询预约套餐数量 -- 饼图
    List<Map<String, Object>> getSetmealReport();



}
