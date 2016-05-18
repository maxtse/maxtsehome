package com.max.tse.javaSelf;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        try {
            throw new RuntimeException("un support issue ticket");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
