package com.max.tse.db.mybatis.proxy;

import com.max.tse.db.mybatis.dao.UserDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-13
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public class MyTestProxyInvocationHandler implements InvocationHandler {

    UserDao userDao;

    public MyTestProxyInvocationHandler (UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getSimpleName());

        return method.invoke(userDao, args);
    }
}
