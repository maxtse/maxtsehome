package com.max.tse.thread.self;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午8:08
 * To change this template use File | Settings | File Templates.
 * Note:sleep()方法不会释放锁
 * 这个测试例子中，仍然会thread1先执行完，然后执行thread2
 * 且调用sleep()方法，必须处理InterruptedException
 */
public class TestSleep {

    private int i = 10;
    private Object object = new Object();
    public static void main(String[] args) {
        TestSleep testSleep = new TestSleep();
        MyThread thread1 = testSleep.new MyThread();
        MyThread thread2 = testSleep.new MyThread();
        thread1.start();
        thread2.start();

    }


    class MyThread extends Thread {
        @Override
        public void run() {
            i++;
            synchronized (object) {
                System.out.println("i: " + i);
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "进入睡眠状态");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("interrupt");

                }
                System.out.println("线程" + Thread.currentThread().getName() + "睡眠结束");
                i++;
                System.out.println("i:" + i);

            }
        }
    }
}
