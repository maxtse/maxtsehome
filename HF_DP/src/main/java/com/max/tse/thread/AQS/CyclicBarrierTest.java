package com.max.tse.thread.AQS;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-27
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws Exception{

        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N);//4个线程都做完了某个动作，那些线程才可以继续做后面的事情
        for (int i =0; i < N; i++){
            new Writer(barrier).start();
        }
    }
    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " 正在写入数据...");
            try {
                Thread.sleep(5000);
                System.out.println("Thread " + Thread.currentThread().getName() + " 写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
