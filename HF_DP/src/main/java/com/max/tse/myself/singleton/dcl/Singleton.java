package com.max.tse.myself.singleton.dcl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午9:52
 * To change this template use File | Settings | File Templates.
 * Note:this is com.max.tse.thread safe case double clock check
 */
public class Singleton {
    private static final Logger  logger = LoggerFactory.getLogger(Singleton.class);
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();



    private static volatile Singleton uniqueInstance;

    private Singleton() {}

    public static Singleton getInstance() {
        try {
            if(uniqueInstance == null) {
                lock.lock();
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }

        } catch (Exception e) {
            logger.error("lock error", e);
        } finally {//解锁
            lock.unlock();
        }
        return uniqueInstance;
        /*if (uniqueInstance == null) {//第一次检查f
            synchronized (Singleton.class) {//同步
                if (uniqueInstance == null) {//第二次检查
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;*/
    }
    //为什么需要两次检查：假设A进入了同步模块 在创建实例且被B感知之前，B进入了第一次检查 这时候仍然检查通过 ，待A离开同步模块
    //B可以进入，然后创建实例，这时，会有A B各自创建的实例

    public static Singleton getInstance1() {
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                if  (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}