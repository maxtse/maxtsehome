package com.max.tse.thread.AQS;

import java.util.concurrent.CyclicBarrier;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-27
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N);//4个线程都做完了某个动作，那些线程才可以继续做后面的事情
        for (int i =0; i < N; i++){

        }
    }
}
