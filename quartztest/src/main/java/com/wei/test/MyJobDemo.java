package com.wei.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version V1.0
 * @author: weiyuan
 * @date: 2019/10/10 20:43
 * @description:
 */
public class MyJobDemo {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }
}
