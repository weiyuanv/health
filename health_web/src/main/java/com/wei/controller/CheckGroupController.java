package com.wei.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wei.api.CheckGroupService;
import com.wei.api.CheckItemService;
import com.wei.common.MessageConstant;
import com.wei.entity.PageResult;
import com.wei.entity.QueryPageBean;
import com.wei.entity.Result;
import com.wei.pojo.CheckGroup;
import com.wei.pojo.CheckItem;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/7 16:09
 * @description:
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    private static final Logger log = Logger.getLogger(CheckGroupController.class);
    @Reference
    private CheckGroupService checkGroupService;

    //新增数据
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        try {
            checkGroupService.add(checkGroup, checkitemIds);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            log.error("Add checkitem error.",e);
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {


        PageResult pageResult = checkGroupService.pageQuery(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    //根据ID查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {

        CheckGroup checkGroup = checkGroupService.findById(id);
        if (checkGroup != null) {
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroup);
            return result;
        }

        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);

    }

    //根据检查组id查询所有对应的检查项
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public List<Integer> findCheckItemIdsByCheckGroupId(@RequestParam Integer id) {
        List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return list;
    }


    //新增数据
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        try {
            checkGroupService.edit(checkGroup, checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            log.error("Add checkitem error.",e);
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }
}
