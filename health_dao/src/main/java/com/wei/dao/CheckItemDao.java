package com.wei.dao;

import com.github.pagehelper.Page;
import com.wei.pojo.CheckItem;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/7 16:33
 * @description:
 */
public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    void delete(Integer id);

    public long findCountByCheckItemId(Integer checkItemId);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);
}
