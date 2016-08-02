package com.max.tse.algorithm.offer;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-30
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates.
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，
 * 则打印出这三个数字能排成的最小数字为321323。 输入： 输入可能包含多个测试样例。 对于每个测试案例，
 * 输入的第一行为一个整数m (1<=m <=100)代表输入的正整数的个数。 输入的第二行包括m个正整数，
 * 其中每个正整数不超过10000000。 输出： 对应每个测试案例， 输出m个数字能排成的最小数字
 */
public class ArrayJoinMin {

    public int test(int[] array, int length) {
        if (length == 1) {
            return array[0];
        }

        if (length == 2) {
            int first = getStartInteger(array[0]);
            int second = getStartInteger(array[1]);
            if (first < second) {
                return Integer.valueOf(first + "" + second);
            }

        }
        return 0;

    }

    private int getStartInteger(int value) {
        if (value / 10 == 0) {
            return value;
        }
        return getStartInteger(value / 10);
    }

    public static void main(String[] args) {
        ArrayJoinMin arrayJoinMin = new ArrayJoinMin();
        System.out.println(arrayJoinMin.getStartInteger(1234));


    }
}
