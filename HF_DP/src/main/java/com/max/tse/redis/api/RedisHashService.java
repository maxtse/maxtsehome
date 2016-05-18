package com.max.tse.redis.api;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
public interface RedisHashService {

    boolean hset(String key, String field, String value);

    String hget(String key, String field);
}
