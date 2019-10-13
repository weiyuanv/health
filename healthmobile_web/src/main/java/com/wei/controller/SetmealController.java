package com.wei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wei.api.SetmealService;
import com.wei.common.MessageConstant;
import com.wei.entity.Result;
import com.wei.pojo.Setmeal;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/13 15:35
 * @description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static final Logger log = Logger.getLogger(SetmealController.class);
    @Reference
    private SetmealService setmealService;

    //获取所有套餐
    @RequestMapping("/getSetmeal")
    public Result getSetMeal() {
        try {
            List<Setmeal> list = setmealService.getSetMeal();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        } catch (Exception e) {
            log.error("getSetMeal error.",e);
        }
        return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);

    }

    //手机端获取套餐详情页面
    @RequestMapping("/findById")
    public Result findById(int id) {
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            log.error("findById error.",e);
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
