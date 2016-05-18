package com.max.tse.jvm;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-12
 * Time: 下午7:26
 * To change this template use File | Settings | File Templates.
 * Note:堆溢出
 * 堆溢出 很好造 就是声明对象就好了
 */
public class HeapOOM {


    static class OOMObject {

    }
    public static void main(String[] arg) {
        List<OOMObject> oomObjectList = Lists.newArrayList();
        while(true) {
            oomObjectList.add(new OOMObject());
        }


    }
}
