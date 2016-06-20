package com.max.tse.thread.self;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 * Note:测试join()方法
 * join方法实际上调用了Object.wait方法
 * 可以看出，Join方法实现是通过wait（小提示：Object 提供的方法）。
 * main 线程调用t.join时，必须能够拿到线程t对象的锁，如果拿不到它是无法wait的 ，刚开的例子t.join(1000)不是说明了main线程等待1 秒，如果在它等待之前，其他线程获取了t对象的锁，它等待时间可不就是1毫秒了 。
 */
public class TestJoin {

    public static void main(String[] args) throws Exception{
        System.out.println("进入线程" + Thread.currentThread().getName());
        TestJoin testJoin = new TestJoin();
        MyThread thread = testJoin.new MyThread();
        thread.start();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "等待");
            thread.join();
            System.out.println("线程" + Thread.currentThread().getName() + "继续执行");
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("进入线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {

            }
            System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
        }
    }
}
