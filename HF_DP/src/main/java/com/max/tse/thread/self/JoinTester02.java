package com.max.tse.thread.self;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 * Note:join()会阻塞线程，必须等到释放锁后才可以继续
 * join()底层是wait(0)，意思是永远等待
 * main 线程调用t.join时，必须能够拿到线程t对象的锁，如果拿不到它是无法wait的 ，刚开的例子t.join(1000)不是说明了main线程等待1 秒，如果在它等待之前，其他线程获取了t对象的锁，它等待时间可不就是1毫秒了 。
 */
public class JoinTester02 implements Runnable{

    Thread thread;

    public JoinTester02(Thread thread) {
        this.thread = thread;
    }

    public void run() {
        synchronized (thread) {
            System.out.println("getObjectLock");
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ReleaseObjectLock");
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new JoinTester01("Three"));
        Thread getLockThread = new Thread(new JoinTester02(thread));
        getLockThread.start();
        thread.start();
        try {
            System.out.println("current-Thread-name " + Thread.currentThread().getName());
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main finished!");
    }
}
