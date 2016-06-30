package com.max.tse.spring.aop.cglib;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-20
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        CglibTarget cglibTarget = (CglibTarget) cglibProxy.getProxy(CglibTarget.class);
        cglibTarget.test();
    }
}
