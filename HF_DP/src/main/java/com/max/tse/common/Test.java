package com.max.tse.common;

import org.apache.commons.lang.math.NumberUtils;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-9
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        String testProductType = "2_901";
        System.out.println(NumberUtils.toInt(testProductType.substring(testProductType.lastIndexOf("_") + 1, testProductType.length())));

        InetSocketAddress inetSocketAddress= new InetSocketAddress("127.0.0.1", 6380);
        System.out.println(inetSocketAddress.isUnresolved());
    }
}
