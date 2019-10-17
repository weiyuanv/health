package com.wei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wei.api.MemberService;
import com.wei.common.MessageConstant;
import com.wei.entity.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/17 17:22
 * @description:
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    private static final Logger log = Logger.getLogger(ReportController.class);

    @Reference
    private MemberService memberService;

    //查询过去1个月每月会员数量
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("months",list);

        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount",memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);

    }
}
