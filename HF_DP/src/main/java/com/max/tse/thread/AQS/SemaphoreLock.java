package com.max.tse.thread.AQS;

import com.max.tse.headfirst.proxy.virtualproxy.ImageProxyTestDrive;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-3-8
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public class SemaphoreLock {

    private static final class Sync extends AbstractQueuedSynchronizer {

        /**
         * 状态代表有几个可以一起处理
         * */
        public Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {

            for (;;) {//自旋锁
                if (hasQueuedPredecessors()) {//有在等待的
                    return -1;
                }
                int current = getState();
                int remaining = current - arg;
                if (remaining < 0 || compareAndSetState(current, remaining)) {
                    return remaining;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int current = getState();
                int next = current + arg;
                if (next < current) {
                    throw new IllegalArgumentException();
                }
                if (compareAndSetState(current, next)) {
                    return true;
                }
            }

        }
    }
}
