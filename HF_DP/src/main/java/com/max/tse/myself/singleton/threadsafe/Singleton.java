package com.max.tse.myself.singleton.threadsafe;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午10:09
 * To change this template use File | Settings | File Templates.
 * Note:线程安全
 */
public class Singleton {

    private static Singleton uniqueInstance;

    private Singleton() {}

    public synchronized static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
}
