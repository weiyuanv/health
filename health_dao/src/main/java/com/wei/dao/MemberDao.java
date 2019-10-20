package com.wei.dao;

import com.wei.pojo.Member;
import org.apache.ibatis.annotations.Param;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 16:50
 * @description:
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String m);

    Integer findTodayNumber(String today);

    Integer findTotalMember(String today);

    Integer findNewMemberCountAfterDate(@Param("date")String date);
}
