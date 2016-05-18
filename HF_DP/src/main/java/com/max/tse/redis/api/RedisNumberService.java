package com.max.tse.redis.api;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
public interface RedisNumberService {

    public long increase(String key);

    public long increaseBy(String key, long num);

    public long decrease(String key);

    public long decreaseBy(String key, long num);

    public double increaseFloat(String key, double num);

}
