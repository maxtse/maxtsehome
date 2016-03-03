package com.max.tse.thread.self;

import com.max.tse.thread.pool.ExecutorsTest;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 * Note:测试join方法
 * join方法底层是通过Object.wait()方法来实现的，因此会释放对象锁，在线程上调用join()方法会释放锁
 * 这里，在main线程里调用at.join()，则会释放main线程的锁，在at里调用bt.join()，依然会释放at占有的锁
 */

class BThread extends Thread {
    public BThread() {
        super("[BThread] Thread");
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " loop at " + i);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}

class AThread extends Thread {
    BThread bt;
    public AThread(BThread bt) {
        super("[AThread] Thread");
        this.bt = bt;
    }
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println(threadName + "start.");
            bt.join();
            System.out.println(threadName + " end");

        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }

}

public class TestDemo {
    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        BThread bt = new BThread();
        AThread at = new AThread(bt);
        try {
            bt.start();
            Thread.sleep(2000);
            at.start();
            at.join();
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(threadName + " end");

    }

}
