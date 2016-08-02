package com.max.tse.redis.api;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public interface RedisBaseService {

    /**
     * 设置有效时间
     * expire stringTest 5
     * @param key
     * @param seconds
     * @return
     * */
    public Long expire(String key, int seconds);

    /**
     * expireat stringTest 1351858600
     * 单位为秒
     * @param key
     * @param time
     * @return
     * */
    public Long expireAt(String key, long time);

    /**
     * key失效
     * persist stringTest
     * 同等效果：set getset
     * @param key
     * */
    public Long persist(String key);

    /**
     *
     * */
    public Long pexpire(String key, long millisecond);

    public Long pexpireAt(String key, long time);

    public boolean exists(String key);

    public long ttl(String key);

    public boolean del(String key);

    public String type(String key);
}
