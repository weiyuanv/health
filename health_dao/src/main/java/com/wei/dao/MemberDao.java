package com.wei.dao;

import com.wei.pojo.Member;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 16:50
 * @description:
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);
}
