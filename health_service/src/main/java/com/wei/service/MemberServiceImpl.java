package com.wei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wei.api.MemberService;
import com.wei.dao.MemberDao;
import com.wei.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/16 14:57
 * @description:
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    //根据手机号查找会员
    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //验证码正确、检验是否为会员
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
