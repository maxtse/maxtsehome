package com.max.tse.redis.lock;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 * Note:对过长的key进行压缩
 */
public interface RedisKeyLengthBeyondHandler {

    String handler(String originKey);
}
