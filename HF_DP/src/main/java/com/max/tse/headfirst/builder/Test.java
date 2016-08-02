package com.max.tse.headfirst.builder;

import com.max.tse.headfirst.builder.po.Man;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-12
 * Time: 下午11:58
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        Man man = Man.newBuilder().
                addFirstName("max").
                addLastName("tse").
                addAge(25).
                addBirthDay(new Date()).
                addCardNum("1222232222222").
                build();
    }
}
