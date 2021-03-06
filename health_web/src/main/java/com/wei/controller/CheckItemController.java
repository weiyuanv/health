package com.wei.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wei.api.CheckGroupService;
import com.wei.api.CheckItemService;
import com.wei.common.MessageConstant;
import com.wei.entity.PageResult;
import com.wei.entity.QueryPageBean;
import com.wei.entity.Result;
import com.wei.pojo.CheckItem;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/checkitem")
public class CheckItemController {

    private static final Logger log = Logger.getLogger(CheckItemController.class);
    @Reference
    private CheckItemService checkItemService;

    @Reference
    private CheckGroupService checkGroupService;

    //新增数据
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {

        try {
            checkItemService.add(checkItem);
            return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            log.error("Add checkitem error.",e);
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

    }

    //显示数据
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    //删除数据
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false,e.getMessage());
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    /**
     * 根据检查项id 查询检查项信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkitem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitem);
        } catch (Exception e){
            log.error("Find checkitem by id cause error.",e);
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    //编辑数据
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {

        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            log.error("Add checkitem error.",e);
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> checkitem = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitem);
        } catch (Exception e){
            log.error("Find checkitem by id cause error.",e);
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id")Integer id){

        try {
            List<Integer> checkItemIds = checkItemService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
