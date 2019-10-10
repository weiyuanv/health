package com.wei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wei.api.SetmealService;
import com.wei.common.RedisConstant;
import com.wei.dao.SetMealDao;
import com.wei.entity.PageResult;
import com.wei.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/10 16:55
 * @description:
 */

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    private JedisPool jedisPool;
    //添加套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        //设置中间表关系
        if (checkgroupIds!=null) {
            setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        }
        //将有效的图片名称存入redis中
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());

    }

    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkGroupId : checkgroupIds) {
                setMealDao.setSetmealAndCheckGroup(checkGroupId,id);
            }
        }

    }

    //分页查找
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setMealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
