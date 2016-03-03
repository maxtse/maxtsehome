package com.max.tse.myself.proxy.learn.simple;

import com.max.tse.myself.proxy.learn.simple.impl.BusinessInvocationHandler;
import com.max.tse.myself.proxy.learn.simple.impl.ProxyBusinessImpl;
import com.max.tse.myself.proxy.learn.simple.impl.RealBusinessImpl;

import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-28
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("simple proxy example-----start");
        BusinessInterface proxyBusiness = new ProxyBusinessImpl(new RealBusinessImpl());
        proxyBusiness.doSomething("max");
        System.out.println("simple proxy example-----end");

        System.out.println("java proxy example-----start");
        BusinessInvocationHandler invocationHandler = new BusinessInvocationHandler(new RealBusinessImpl());
        Object  businessInterface =  Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] {BusinessInterface.class, BusinessInterface2.class}, invocationHandler);
        ((BusinessInterface)businessInterface).doSomething("max");
        ((BusinessInterface2) businessInterface).doSomething();
        System.out.println("java proxy example-----end");




    }
}
