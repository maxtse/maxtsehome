package com.max.tse.redis.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午7:00
 * To change this template use File | Settings | File Templates.
 */
public class ResultUtils {

    private ResultUtils() {}

    public static boolean analyseResult(String jedisResult) {
        return StringUtils.containsIgnoreCase(jedisResult, "ok");
    }

}
