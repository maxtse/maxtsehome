package com.max.tse.spring.reflect;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-29
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("current loader:" + loader);
        System.out.println("parent loader:" + loader.getParent());
        System.out.println("grandparent loader:" + loader.getParent().getParent());
    }
}
