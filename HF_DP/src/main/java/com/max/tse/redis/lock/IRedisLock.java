package com.max.tse.redis.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-10
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
public interface IRedisLock {

    /**
     * 加锁
     * @param prefix key前缀
     * @param time 失效时间
     * @param timeUnit 时间单位
     * @return true/false
     * */
    public boolean tryLock(String prefix, String lockKey, int time, TimeUnit timeUnit);

    /**
     * 解锁
     * @param prefix key前缀
     * */
    public boolean unLock(String prefix, String lockKey);
}
