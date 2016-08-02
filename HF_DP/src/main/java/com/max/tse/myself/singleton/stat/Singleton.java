package com.max.tse.myself.singleton.stat;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午10:01
 * To change this template use File | Settings | File Templates.
 * Note:立刻创建 不在延迟初始化
 */
public class Singleton {

    private Singleton uniqueInstance = new Singleton();

    private Singleton () {}

    public Singleton getInstance() {
        return uniqueInstance;
    }
}
