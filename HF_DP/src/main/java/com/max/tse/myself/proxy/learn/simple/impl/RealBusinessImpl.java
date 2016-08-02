package com.max.tse.myself.proxy.learn.simple.impl;

import com.max.tse.myself.proxy.learn.simple.BusinessInterface;
import com.max.tse.myself.proxy.learn.simple.BusinessInterface2;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-28
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class RealBusinessImpl implements BusinessInterface,BusinessInterface2 {

    @Override
    public void doSomething(String username) {
        System.out.println("real do something for username : " + username);

    }

    @Override
    public void doSomething() {
        System.out.println("real do something without args");
    }
}
