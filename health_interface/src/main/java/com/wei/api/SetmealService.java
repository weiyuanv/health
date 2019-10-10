package com.wei.api;

import com.github.pagehelper.Page;
import com.wei.entity.PageResult;
import com.wei.pojo.Setmeal;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/10 16:54
 * @description:
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);
}
