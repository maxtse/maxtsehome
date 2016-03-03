package com.max.tse.thread.AQS;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-10
 * Time: 下午9:35
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) throws Exception{

        final ReentrantLock reentrantLock = new ReentrantLock();
        final Condition condition = reentrantLock.newCondition();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    System.out.println("我要等一个信号" + this);
                    condition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("拿到一个信号!! " + this);
                reentrantLock.unlock();
            }
        }, "waitThread1");
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                System.out.println("我拿到锁了");
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                condition.signalAll();
                System.out.println("我发了一个信号");
                reentrantLock.unlock();

            }
        }, "singalThread");
        thread1.start();

    }
}
