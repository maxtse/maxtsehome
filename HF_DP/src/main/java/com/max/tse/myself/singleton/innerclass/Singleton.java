package com.max.tse.myself.singleton.innerclass;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午10:17
 * To change this template use File | Settings | File Templates.
 */
public class Singleton {

    private Singleton() {}

    private final static class SingletonHolder{
        private static final Singleton INSTANCE = new Singleton();
    }
    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
