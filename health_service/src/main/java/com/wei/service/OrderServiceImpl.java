package com.wei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wei.api.OrderService;
import com.wei.common.DateUtils;
import com.wei.common.MessageConstant;
import com.wei.dao.MemberDao;
import com.wei.dao.OrderDao;
import com.wei.dao.OrderSettingDao;
import com.wei.entity.Result;
import com.wei.pojo.Member;
import com.wei.pojo.Order;
import com.wei.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 16:12
 * @description:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    //提交预约订单的 详细处理方法



    @Override
    public Result order(Map map) {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = null;
        OrderSetting orderSetting = null;
        try {
            date = DateUtils.parseString2Date(orderDate);
            orderSetting = orderSettingDao.findCountByDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        //可预约人数
        int number = orderSetting.getNumber();
        //已预约人数
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //3、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (member == null) {
            //如果不是会员，注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            member.setSex((String) map.get("sex"));
            member.setName((String) map.get("name"));
            //执行注册
            memberDao.add(member);

        } else {
            //是会员
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));

            //是会员查询是否重复预约
            Order queryOrder = orderDao.findByCondition(order);
            if (queryOrder == null) {
                return  new Result(false, MessageConstant.HAS_ORDERED);
            }
        }





        //保存预约信息到预约表
        Order order = new Order(member.getId(),
                date,
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

        //5、预约成功，更新当日的已预约人数
        orderSettingDao.updateReservationsByOrderDate(date);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    //预约成功数据显示
    @Override
    public Map findById(Integer id) {

        Map map = orderDao.findById(id);
        Date date = (Date) map.get("orderDate");
        try {
            map.put("orderDate", DateUtils.parseDate2String(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
