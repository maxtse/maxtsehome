package com.max.tse.thread.self;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午8:20
 * To change this template use File | Settings | File Templates.
 * Note:测试interrupt()方法
 * interrupt()方法只可以中断阻塞的线程
 * 对于非阻塞的线程，没有效果
 */
public class TestInterrupt {

    private static final String FLAGSHIP_REFUND_URL = "http://%s/tts/ordercenter/flagOrderCancel?from=flagorder_cancel&id=%s&orderNo=%s";


    public static void main(String[] args) {
        TestJson  testJoin = new TestJson();
        testJoin.setOrderNo("fdf");
        List<String> strings = Lists.newArrayList();
        strings.add("fd");
        testJoin.setStrings(strings);
        System.out.println(JSON.toJSONString(testJoin, SerializerFeature.WriteClassName));


        System.out.println(String.format(FLAGSHIP_REFUND_URL, "abc.trade.qunar.com", 42343, "xdr201609930"));

    }
}
