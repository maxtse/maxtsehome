package com.max.tse.thread.lock;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-31
 * Time: 上午9:50
 * To change this template use File | Settings | File Templates.
 *
 */

class Thread1 implements Runnable {

    private Object left;
    private Object right;

    Thread1(Object left, Object right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public void run() {
        synchronized (left) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }
            synchronized (right){
                System.out.println("i am left right");
            }
        }
    }
}

class Thread2 implements Runnable {
    private Object left;
    private Object right;

    Thread2(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        synchronized (right) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }
            synchronized (left){
                System.out.println("i am left right");
            }
        }
    }
}
public class LeftRightDeadLock {

    private static final Object left = new Object();
    private static final Object right = new Object();

    public static void main(String[] args){
        Thread thread1 = new Thread(new Thread1(left, right));
        Thread thread2= new Thread(new Thread2(left, right));
        Thread thread = new Thread();
        thread1.start();
        thread2.start();
    }
}
