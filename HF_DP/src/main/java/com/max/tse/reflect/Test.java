package com.max.tse.reflect;

import com.alibaba.fastjson.JSON;
import com.max.tse.reflect.po.Father;
import com.max.tse.reflect.po.Son;
import com.max.tse.reflect.po.Son2;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-25
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void testFatherAndSon(Class clazz) {
        try {
            Father father = (Father) clazz.newInstance();
            father.setAge(1);
            father.setId(12);
            Son son = (Son) father;
            son.setName("test");
            System.out.println(JSON.toJSONString(son));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testJson() {
        Son son = new Son();
        son.setName("fd");
        Father father = son;
        System.out.println(JSON.toJSONString(father));
    }

    public static void main(String[] args) {
        testFatherAndSon(Son.class);


        testJson();

    }
}
