package com.wei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wei.api.MemberService;
import com.wei.api.ReportService;
import com.wei.common.DateUtils;
import com.wei.dao.MemberDao;
import com.wei.dao.OrderDao;
import com.wei.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/19 16:05
 * @description:
 */

/**
 * reportDate,//今日日期
 * todayNewMember//今日新增会员数  todayOrderNumber//今日预约数 todayVisitsNumber//今日到诊数
 * totalMember,//总会员数
 *
 * thisWeekNewMember//本周新增会员数 thisWeekOrderNumber//本周预约数 thisWeekVisitsNumber//本周到诊数
 *
 * thisMonthOrderNumber//本月预约数 thisMonthVisitsNumber //本月到诊数thisMonthNewMember//本月新增会员数
 * hotSetmeal:热门套餐4个
 * @return
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    //运营数据统计
    @Override
    public Map getBusinessReportData() {


        try {
            //今日日期
            String today = DateUtils.parseDate2String(DateUtils.getToday());

            //获得本周周一
            String thisWeekMonday  = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());

            //获得本月第一天
            String firstDay4ThisMonth   = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

            //今日新增会员数
            Integer todayNewMember = memberDao.findTodayNumber(today);

            //总会员数
            Integer totalMember  = memberDao.findTotalMember(today);

            //本周新增会员数
            Integer thisWeekNewMember = memberDao.findNewMemberCountAfterDate(thisWeekMonday);

            //本月新增会员数
            Integer thisMonthNewMember = memberDao.findNewMemberCountAfterDate(firstDay4ThisMonth);

            //今日预约数
            Integer todayOrderNumber = orderDao.findTodayOrderNumber(today);

            //本周预约数
            Integer thisWeekOrderNumber = orderDao.findOrderNumberAfterDate(thisWeekMonday);

            //本月预约数
            Integer thisMonthOrderNumber = orderDao.findOrderNumberAfterDate(firstDay4ThisMonth);

            //今日到诊数
            Integer todayVisitsNumber = orderDao.findTodayVisitsNumber(today);

            //本周到诊数
            Integer thisWeekVisitsNumber = orderDao.findVisitsNumberAfterDate(thisWeekMonday);

            //本月到诊数
            Integer thisMonthVisitsNumber = orderDao.findVisitsNumberAfterDate(firstDay4ThisMonth);

            //热门套餐4个
            List<Map<String,Object>> hotSetmeal  = orderDao.findHotSetmeal();

            //定义map封装页面所需数据
            Map<String,Object> result = new HashMap<>();

            result.put("reportDate",today);
            result.put("todayNewMember",todayNewMember);
            result.put("totalMember",totalMember);
            result.put("thisWeekNewMember",thisWeekNewMember);
            result.put("thisMonthNewMember",thisMonthNewMember);
            result.put("todayOrderNumber",todayOrderNumber);
            result.put("thisWeekOrderNumber",thisWeekOrderNumber);
            result.put("thisMonthOrderNumber",thisMonthOrderNumber);
            result.put("todayVisitsNumber",todayVisitsNumber);
            result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            result.put("hotSetmeal",hotSetmeal);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }






    }
}
