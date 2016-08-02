package com.max.tse.jvm;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-24
 * Time: 下午6:51
 * To change this template use File | Settings | File Templates.
 */
public class TestAllocation {

    private static final int _1MB = 1024 * 1024;
    /**
     * -Xms20m -Xmx20m -Xmn10m -SurvivorRatio=8
     * 新生代10m
     * eden 8m
     * 分配allocation4的时候发现eden不够了 然后minor gc 然后分配到survivor 发现1mb可也不够 然后直接分配到old
     * */
    private static void testAllocation() {
        byte[] allocation1,allocation2,allocation3,allocation4;

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
