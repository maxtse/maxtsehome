package com.max.tse.redis.lock.impl;

import com.max.tse.common.utils.MD5Util;
import com.max.tse.redis.lock.RedisKeyLengthBeyondHandler;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
public class MD5RedisKeyLengthBeyondHandler implements RedisKeyLengthBeyondHandler {
    @Override
    public String handler(String originKey) {
        return MD5Util.hash(originKey);
    }
}
