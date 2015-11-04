package com.max.tse.myself.singleton.classic;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午9:48
 * To change this template use File | Settings | File Templates.
 * Note: this is not com.max.tse.thread safe！
 */
public class Singleton {
    private static Singleton uniqueInstance;//私有静态变量，共享

    private Singleton() {}//私有的构造方法，防止通过new操作来生成新的实例

    public static Singleton getInstance() {//获得实例的方法 如果为null就去实例化 如果不为null 就返回
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
}
