package com.max.tse.thread.self;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午8:38
 * To change this template use File | Settings | File Templates.
 * Note:测试sleep和wait方法
 */
public class TestWait implements Runnable{
    int num = 10;

    public void firstMethod() throws Exception {
        synchronized(this) {
            num += 100;
            System.out.println(num);

        }
    }

    public void secondMethod() throws Exception {
        synchronized (this) {
            this.wait(2000);
            num *= 200;
        }
    }

    @Override
    public void run() {
        try {
            firstMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
        TestWait testWait = new TestWait();

        Thread thread = new Thread(testWait);
        thread.start();

        testWait.secondMethod();
        System.out.println("threadTest.number " + testWait.num);
    }
}
