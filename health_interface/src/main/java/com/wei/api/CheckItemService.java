package com.wei.api;

import com.wei.entity.PageResult;
import com.wei.pojo.CheckItem;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/7 16:29
 * @description:
 */
public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);

    List<CheckItem>  findAll();

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
}
