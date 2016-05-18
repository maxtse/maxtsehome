package com.max.tse.common.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class MD5Util {

    private MD5Util() {}

    public static String hash(String key) {
        Hasher hashing = Hashing.md5().newHasher();
        return hashing.putString(key, Charsets.UTF_8).hash().toString();
    }
}
