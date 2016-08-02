package com.max.tse.common.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.List;

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

    public static HashCode hashCode(String key) {
        Hasher hashing = Hashing.md5().newHasher();
        return hashing.putString(key, Charsets.UTF_8).hash();
    }

    public static void main(String[] args) {
        List<String> testString = Lists.newArrayList();
        testString.add("test");
        testString.add("test1");
        Optional<String> optional = Iterators.tryFind(testString.iterator(), new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.equalsIgnoreCase("1");
            }
        });
        System.out.println(optional.isPresent() );
        System.out.println(hash("abc20160606123"));
    }
}
