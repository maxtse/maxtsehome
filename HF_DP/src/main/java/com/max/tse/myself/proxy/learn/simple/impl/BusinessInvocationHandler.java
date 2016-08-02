package com.max.tse.myself.proxy.learn.simple.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-28
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class BusinessInvocationHandler implements InvocationHandler {

    private Object  realBusiness;

    public BusinessInvocationHandler(Object realBusiness) {
        this.realBusiness = realBusiness;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("（代理）调用处理器被激活=====");
        System.out.println("“代理者对象”：" + proxy.getClass().getName());
        System.out.println("“外部模块/外部系统”调用的方法名：" + method.getName());

        System.out.println("---------正式业务执行前；");
        Object resultObject = method.invoke(this.realBusiness, args);
        System.out.println("---------正式业务执行后；");

        return resultObject;
    }
}
