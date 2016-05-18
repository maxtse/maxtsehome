package com.max.tse.redis.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class ArgumentValidateUtils {

    private ArgumentValidateUtils(){}

    public static void validateKeyAndValue(String key, String value) {
        validateKey(key);
        validateValue(value);
    }

    public static void validateKeyValueAndSecond(String key, String value, int seconds) {
        validateKeyAndValue(key, value);
        validateSecond(seconds);
    }

    public static void validateKeyAndSecond(String key, int second) {
        validateKey(key);
        validateSecond(second);
    }

    public static void validateKeyAndTime(String key, long time) {
        validateKey(key);
        validateTime(time);
    }

    public static void validateKeyAndMillSecond(String key, long millSeconds) {
        validateKey(key);
        validateMillSeconds(millSeconds);
    }

    public static void validateKeyFieldValue(String key, String field, String value) {
        validateKeyAndValue(key, value);
        validateField(field);
    }

    public static void validateKeyField(String key, String filed) {
        validateField(filed);
        validateKey(key);
    }
    public static void validateKey(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key));
    }

    public static void validateTime(long time) {
        Preconditions.checkArgument(time > 0);
    }

    public static void validateSecond(int seconds) {
        Preconditions.checkArgument(seconds > 0);
    }

    public static void validateMillSeconds(long millSeconds) {Preconditions.checkArgument(millSeconds > 0);}

    public static void validateValue(String value) {
        Preconditions.checkArgument(StringUtils.isNotBlank(value));
    }

    public static void validateField(String field) {Preconditions.checkArgument(StringUtils.isNotBlank(field));}
}
