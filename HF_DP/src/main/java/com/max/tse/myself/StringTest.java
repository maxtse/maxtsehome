package com.max.tse.myself;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-15
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
public class StringTest {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        String d = "ab" + "c";

        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a == d);
        System.out.println(b == c);
        System.out.println(b == d);
        System.out.println(c == d);

        List<String> toCompare = Lists.newArrayList();
        toCompare.add("12");
        Collections.sort(toCompare, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(toCompare);
    }
}
