package com.max.tse.thread.self;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-11
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadNum = new AtomicInteger(1);
    private final String mPrefix;

    private final boolean mDaemo;

    private final ThreadGroup mGroup;

    public NamedThreadFactory(String mPrefix) {
        this(mPrefix, true);

    }

    public NamedThreadFactory(String mPrefix, boolean mDaemo) {
        this.mPrefix = mPrefix + "-thread-";
        this.mDaemo = mDaemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();

    }

    @Override
    public Thread newThread(Runnable r) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, r, name, 0);
        ret.setDaemon(mDaemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {return mGroup;}
}
