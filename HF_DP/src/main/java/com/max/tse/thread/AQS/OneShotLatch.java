package com.max.tse.thread.AQS;


import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-10
 * Time: 下午9:35
 * To change this template use File | Settings | File Templates.
 */
public class OneShotLatch  {

    private final Sync sync = new Sync();

    private class Sync extends AbstractQueuedSynchronizer {

        /**
         * 小于0 代表需要进行锁确认
         * */
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 1 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);//打开
            return true;
        }
    }

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws Exception{
        sync.acquireSharedInterruptibly(0);
    }



}
