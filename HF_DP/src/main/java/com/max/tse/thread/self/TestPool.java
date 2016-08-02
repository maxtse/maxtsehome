package com.max.tse.thread.self;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-11
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public class TestPool {

    private final AtomicInteger taskNum = new AtomicInteger(1);
    /**
     * 参数解释
     * 核心线程数字
     * 最大线程数字
     * 活跃时间
     * 单位
     * 阻塞队列
     * 线程工厂
     * 拒绝策略
     * */
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(5,5, 10, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(5),
            new NamedThreadFactory("testpool"), new ThreadPoolExecutor.CallerRunsPolicy());

    private static final ExecutorService CACHE_THREAD_POOL = Executors.newCachedThreadPool(new NamedThreadFactory("testpool.cache"));

    private static final ExecutorService SINGLE_THREAD_POOL = Executors.newSingleThreadExecutor(new NamedThreadFactory("testpool.single"));

    private static final ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(100, new NamedThreadFactory("testpool.fix"));

    private static final ExecutorService SCHEDULE_THREAD_POOL = Executors.newScheduledThreadPool(100, new NamedThreadFactory("testpool.schedule"));


    public void test() {
        List<TestTask> testTasks = Lists.newArrayList();

        for (int i = 0; i < 20; i++) {
            testTasks.add(new TestTask(i));
        }

        try {
            List<Future<String>> futures = THREAD_POOL.invokeAll(testTasks, 10, TimeUnit.SECONDS);

            for (Future<String> future : futures) {
                System.out.println(taskNum.getAndIncrement() + "....." + System.currentTimeMillis());
                System.out.println(future.get());
                System.out.println("......" + System.currentTimeMillis());
            }

        } catch (Exception e) {
            System.out.println("test future...." + e.getMessage());
        }

    }

    private class TestTask implements Callable<String> {

        private int i;

        public TestTask() {}

        public TestTask(int i) {
            this.i = i;

        }
        @Override
        public String call() throws Exception {

            try {
                System.out.println("....." + i + "i am ok ");
            } catch (Exception e) {
                System.out.println("testtask........." + e.getMessage());
                return "fail";
            }

            return "success";
        }
    }
    public static void main(String[] args) {
        System.out.println("start "+ System.currentTimeMillis());
        TestPool testPool = new TestPool();
        testPool.test();
        System.out.println("end "+ System.currentTimeMillis());

        long start = 1462934387871l;
        long end = 1462933922080l;
        System.out.println((end - start)/1000);



    }


}
