package com.wei.dao;

import com.github.pagehelper.Page;
import com.wei.pojo.CheckGroup;
import com.wei.pojo.CheckItem;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/8 15:58
 * @description:
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map map);

    Page<CheckGroup> selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    //查询所有检查组
    List<CheckGroup> findAll();
}
