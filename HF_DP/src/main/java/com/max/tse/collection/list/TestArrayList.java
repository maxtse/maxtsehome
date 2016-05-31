package com.max.tse.collection.list;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-30
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
public class TestArrayList {

    public void testInit() {
        ArrayList<String> arrayList = new ArrayList(2);//默认大小是10
        arrayList.add("test");//第一次加入一个元素，会保证初始化capacity ：minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        arrayList.add("test1");
        arrayList.add("test1");//扩容 1.5倍
        arrayList.ensureCapacity(20);//手动扩容
        //扩容 grow(int minCapacity)
        //private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8 默认最大的数组尺寸 hugeCapacity(int minCapacity)最大Integer.MAX_VALUE

        ArrayList<String> arrayList1 = new ArrayList<>(4);
        arrayList1.add("test");
        arrayList1.add("test1");
        arrayList1.add("notest");
        arrayList1.add("notest");
        arrayList1.add("notest1");
        ArrayList<String> arrayList2 = new ArrayList<>(4);
        arrayList2.add("test");
        arrayList2.add("test2");
        arrayList2.add("notest");
        arrayList2.add("notest2");

        System.out.println(JSON.toJSONString(arrayList1.retainAll(arrayList2)));//求交集
        System.out.println(JSON.toJSONString(arrayList1));

        //test subList 对子 和原来list的修改都会对应到原来的数组和子list的数组上 因为用的都是原来的数组
        ArrayList<String> arrayList3 = new ArrayList<>(5);
        arrayList3.add("test0");
        arrayList3.add("test1");
        arrayList3.add("test2");
        arrayList3.add("test3");
        arrayList3.add("test4");

        List<String> subList = arrayList3.subList(0, 2);

        List<String> subList2 = subList.subList(0, 1);

        System.out.println("parent list is" + JSON.toJSONString(arrayList3) + "subList is" + JSON.toJSONString(subList) + "subList1 is" + JSON.toJSONString(subList2));

        subList.set(0, "new test0");
        System.out.println("parent list is" + JSON.toJSONString(arrayList3) + "subList is" + JSON.toJSONString(subList) + "subList1 is" + JSON.toJSONString(subList2));


        //test toArray()
        List<Car> cars = new ArrayList<Car>();
        cars.add(new LightCar());
        cars.add(new LightCar());

        LightCar[] lightCars = cars.toArray(new LightCar[3]);
        System.out.println(JSON.toJSONString(lightCars));





    }

    public static void main(String[] args) {
        TestArrayList testArrayList = new TestArrayList();
        testArrayList.testInit();

    }

    class Car {

    }
    class LightCar extends Car{

    }
}
