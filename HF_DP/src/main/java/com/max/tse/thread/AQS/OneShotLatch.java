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

    }
}
