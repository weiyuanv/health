package com.wei.common;

import com.aliyuncs.exceptions.ClientException;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/14 14:32
 * @description:
 */
public class SMSUtilsTest {
    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage("SMS_175495198","18852072000","1234");
    }
}
