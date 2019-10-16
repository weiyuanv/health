package com.wei.api;

import com.wei.pojo.Member;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/16 14:56
 * @description:
 */
public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void add(Member member);
}
