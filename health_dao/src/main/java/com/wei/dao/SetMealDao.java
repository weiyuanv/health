package com.wei.dao;

import com.github.pagehelper.Page;
import com.wei.entity.PageResult;
import com.wei.pojo.CheckGroup;
import com.wei.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/10 16:58
 * @description:
 */
public interface SetMealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("checkGroupId") Integer checkGroupId, @Param("id") Integer id);

    Page<Setmeal> findByCondition(@Param("queryString") String queryString);

    List<Setmeal> getSetMeal();

    Setmeal findById(int id);
}
