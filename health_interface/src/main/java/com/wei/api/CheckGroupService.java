package com.wei.api;

import com.wei.entity.PageResult;
import com.wei.pojo.CheckGroup;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/8 15:48
 * @description:
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);
}
