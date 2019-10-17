package com.wei.api;

import com.wei.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/16 14:56
 * @description:
 */
public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void add(Member member);

    //查询过去1个月每月会员数量
    List<Integer> findMemberCountByMonth(List<String> list);
}
