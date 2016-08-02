package com.max.tse.web;

import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-3-24
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        try {
           System.out.println(URLDecoder.decode("2015-02-09", "UTF-8"));
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public static void  test() {
        synchronized(new Object()) {

        }

    }
}
