package com.max.tse.myself.proxy.learn.simple.impl;

import com.max.tse.myself.proxy.learn.simple.BusinessInterface;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-28
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class ProxyBusinessImpl implements BusinessInterface {

    private BusinessInterface realBusinessImpl;

    public ProxyBusinessImpl(BusinessInterface realBusinessImpl) {
        this.realBusinessImpl = realBusinessImpl;
    }

    @Override
    public void doSomething(String username) {
        System.out.println("real do something before for username: " + username);
        realBusinessImpl.doSomething(username);
        System.out.println("real do something after for username: " + username);

    }
}
