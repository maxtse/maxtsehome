package com.max.tse.thread.AQS;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-23
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class CountDownLatchTest {

    private ExecutorService THREAD_POOL = new ThreadPoolExecutor(5, 5, 50, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(5));

    private void test() {
        try {
            List<Worker> workerList = Lists.newArrayList();

            workerList.add(new Worker());
            workerList.add(new Worker());
            workerList.add(new Worker());
            workerList.add(new Worker());
            workerList.add(new Worker());


            final CountDownLatch countDownLatch = new CountDownLatch(workerList.size());
            List<Future> futures = Lists.newArrayList();
            System.out.println(" i start waiting " + new Date());
            for (final Worker worker : workerList) {
                Future<Object> future = THREAD_POOL.submit(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        Object result = null;
                        try {
                            result = worker.call();
                            return result;

                        } finally {
                            System.out.println(result);
                            countDownLatch.countDown();
                        }
                    }
                });
                futures.add(future);
            }
            countDownLatch.await();//acquireSharedInterruptibly()
            System.out.println("end " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Worker implements Callable {
        @Override
        public Object call() throws Exception {
            try {
                System.out.println("i awake");
                Thread.sleep(5000);
                return "wake";
            } catch (Exception e) {
                return "";
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        countDownLatchTest.test();

    }
}
