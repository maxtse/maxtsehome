package com.max.tse.myself.singleton.innerclass;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-10
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class Singleton1 {

    private Singleton1() {}

    private static ReentrantLock lock;

    private static volatile Singleton1 uniq;

    public static Singleton1 getInstance() {
        try {
            lock.lock();
            if (uniq == null) {
                uniq = new Singleton1();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return uniq;




    }
}
